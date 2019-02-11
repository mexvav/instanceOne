package core.jpa.entity;

import com.google.common.collect.Maps;
import core.jpa.Constants;
import core.jpa.ReloadableSessionFactory;
import core.jpa.dao.DAOEntity;
import core.jpa.entity.building.BuildingException;
import core.jpa.entity.building.BuildingService;
import core.jpa.entity.entities.EntityDescription;
import core.jpa.interfaces.HasEntityCode;
import core.jpa.mapping.MappingService;
import javassist.CannotCompileException;
import javassist.CtClass;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.*;
import java.util.function.Supplier;

@Component
public class EntityService {
    private ReloadableSessionFactory sessionFactory;

    private Map<String, Class<?>> currentEntities;
    private Map<String, Class<?>> entities;
    private Map<String, EntityBlank> entitiesBlank;

    private BuildingService buildingService;
    private MappingService mappingService;
    private DAOEntity dao;

    @Autowired
    EntityService(SessionFactory sessionFactory, BuildingService buildingService,
                  MappingService mappingService, DAOEntity dao) {
        if (sessionFactory instanceof ReloadableSessionFactory) {
            this.sessionFactory = (ReloadableSessionFactory) sessionFactory;
        } else {
            throw new EntityServiceException(EntityServiceException.ExceptionCauses.SESSION_FACTORY_IS_NOT_RELOADABLE);
        }
        this.buildingService = buildingService;
        this.mappingService = mappingService;
        this.dao = dao;

        reloadCurrentEntities();
        initializeEntities();
    }

    /**
     * Make some actions with reload session factory. Rollback if exception
     *
     * @param action some actions with reload session factory
     */
    public <R> R actionWithReloadSessionFactory(Supplier<R> action) {
        reloadCurrentEntities();
        Map<String, Class<?>> entities = getEntities();
        Map<String, EntityBlank> entitiesBlank = getEntitiesBlank();
        try {
            R result = action.get();
            reloadService();
            return result;
        } catch (Throwable e) {
            this.entities = entities;
            this.entitiesBlank = entitiesBlank;
            throw new EntityServiceException(e.getMessage());
        }
    }

    /**
     * Rebuild session factory
     * not safety, use method actionWithReloadSessionFactory or annotation {@link WithReloadSessionFactory}
     */
    public void reloadService() {
        getEntities().forEach((code, entity) -> {
            if (!getCurrentEntities().containsKey(code)) {
                sessionFactory.getEntities().add(entity);
            }
        });

        getCurrentEntities().forEach((code, entity) -> {
            if (!getEntities().containsKey(code)) {
                sessionFactory.getEntities().remove(entity);
            }
        });

        sessionFactory.reloadSessionFactory();
        reloadCurrentEntities();
    }

    /**
     * Create new entity in runtime
     *
     * @param entityBlanks - entity blanks
     */
    @WithReloadSessionFactory
    public void createEntity(final EntityBlank... entityBlanks) {
        for (EntityBlank entityBlank : entityBlanks) {
            if (isEntityExist(entityBlank.getCode())) {
                continue;
            }
            Class entity = buildEntity(entityBlank);
            getEntities().put(entityBlank.getCode(), entity);
            getEntitiesBlank().put(entityBlank.getCode(), entityBlank);
            saveDescription(entityBlank);
        }
    }

    /**
     * Delete entity by code
     *
     * @param codes some codes of entity
     */
    @WithReloadSessionFactory
    public void removeEntity(String... codes) {
        Arrays.stream(codes).forEach(code -> {
            dao.dropTable(code);
            getEntities().remove(code);
            getEntitiesBlank().remove(code);
        });
    }

    /**
     * Warning! deleting all entities
     */
    @WithReloadSessionFactory
    public void hardClean() {
        Set<String> tables = dao.getAllTables();
        tables.remove(Constants.EntityDescription.TABLE);
        tables.forEach(table -> dao.dropTable(table));
        dao.cleanTable(Constants.EntityDescription.ENTITY_NAME);

        getEntities().clear();
        getEntitiesBlank().clear();
    }

    /**
     * Is entity with {@link HasEntityCode} exist
     *
     * @param code entity code
     */
    public boolean isEntityExist(String code) {
        return getEntities().containsKey(code);
    }

    /**
     * Get entity blank by code
     *
     * @param code code of entity
     * @throws EntityServiceException if entity not found
     */
    public EntityBlank getEntityBlank(String code) {
        if (isEntityExist(code)) {
            return getEntitiesBlank().get(code);
        }
        throw new EntityServiceException(EntityServiceException.ExceptionCauses.ENTITY_IS_NOT_EXIST, code);
    }

    public Class<?> getEntity(String code) {
        if (isEntityExist(code)) {
            return getEntities().get(code);
        }
        throw new EntityServiceException(EntityServiceException.ExceptionCauses.ENTITY_IS_NOT_EXIST, code);
    }

    /**
     * Save information about entityBlank in db. See {@link EntityDescription}
     *
     * @param entityBlank entityBlank for saving description
     */
    private void saveDescription(EntityBlank entityBlank) {
        String json = mappingService.mapping(entityBlank, String.class);
        EntityDescription description = new EntityDescription();
        description.setCode(entityBlank.getCode());
        description.setDescription(json);
        dao.save(description);
    }

    /**
     * Build entity
     *
     * @param entityBlank - entity blank
     */
    private Class buildEntity(EntityBlank entityBlank) {
        CtClass entityCtClass = buildingService.build(null, entityBlank);
        try {
            return entityCtClass.toClass();
        } catch (CannotCompileException e) {
            throw new BuildingException(e.getMessage());
        }
    }

    /**
     * Init all entities from db by {@link EntityDescription}
     */
    @WithReloadSessionFactory
    private void initializeEntities() {
        @SuppressWarnings("unchecked")
        List<EntityDescription> entities = dao.getAll(EntityDescription.entityName);
        for (EntityDescription description : entities) {
            EntityBlank entityBlank = mappingService.mapping(description.getDescription(), EntityBlank.class);
            Class entity = buildEntity(entityBlank);
            getEntitiesBlank().put(entityBlank.getCode(), entityBlank);
            getEntities().put(entityBlank.getCode(), entity);
        }
    }

    /**
     * Get all entities
     */
    private Map<String, Class<?>> getEntities() {
        if (null == entities) {
            entities = Maps.newHashMap();
        }
        return entities;
    }

    /**
     * Get all entitiesBlank
     */
    private Map<String, EntityBlank> getEntitiesBlank() {
        if (null == entitiesBlank) {
            entitiesBlank = Maps.newHashMap();
        }
        return entitiesBlank;
    }

    private Map<String, Class<?>> getCurrentEntities() {
        if (null == currentEntities) {
            currentEntities = Maps.newHashMap();
        }
        return currentEntities;
    }

    private void reloadCurrentEntities() {
        currentEntities = Maps.newHashMap(getEntities());
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface WithReloadSessionFactory {
    }

    @Aspect
    @Component
    public class WithReloadSessionFactoryAspect {

        @Around("@annotation(core.jpa.entity.EntityService.WithReloadSessionFactory)")
        public Object withReloadSessionFactory(ProceedingJoinPoint joinPoint) {
            return actionWithReloadSessionFactory(() -> {
                try {
                    return joinPoint.proceed();
                } catch (Throwable e) {
                    throw new EntityServiceException(e.getMessage());
                }
            });
        }
    }
}
