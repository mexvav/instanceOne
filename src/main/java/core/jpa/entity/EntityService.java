package core.jpa.entity;

import com.google.common.collect.Maps;
import core.jpa.ReloadableSessionFactory;
import core.jpa.dao.DAOEntity;
import core.jpa.entity.building.BuildingException;
import core.jpa.entity.entities.EntityDescription;
import core.jpa.entity.building.BuildingService;
import core.jpa.mapping.MappingService;
import javassist.CannotCompileException;
import javassist.CtClass;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class EntityService {
    private ReloadableSessionFactory sessionFactory;
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

        initializeEntities();
    }

    /**
     * Create new entity in runtime
     *
     * @param entityBlank - entity blank
     */
    public void createEntity(EntityBlank entityBlank) {
        if (getEntities().containsKey(entityBlank.getCode())) {
            return;
        }

        Class entity = buildEntity(entityBlank);
        getEntities().put(entityBlank.getCode(), entity);
        getEntitiesBlank().put(entityBlank.getCode(), entityBlank);
        try {
            reloadService();
            saveDescription(entityBlank);
        } catch (Exception e) {
            getEntities().remove(entityBlank.getCode());
            getEntitiesBlank().remove(entityBlank.getCode());
            throw new EntityServiceException(EntityServiceException.ExceptionCauses.SERVICE_RELOAD_ERROR, e.getMessage());
        }
    }

    private Class buildEntity(EntityBlank entityBlank) {
        CtClass entityCtClass = buildingService.build(null, entityBlank);
        try {
            return entityCtClass.toClass();
        } catch (CannotCompileException e) {
            throw new BuildingException(e.getMessage());
        }
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

    public boolean isEntityExist(String code) {
        return getEntities().containsKey(code);
    }

    /**
     * Rebuild session factory, load new entity
     */
    public void reloadService() {
        sessionFactory.getEntities().addAll(getEntities().values());
        sessionFactory.reloadSessionFactory();
    }

    /**
     * Save information about entityBlank in db. See {@link EntityDescription}
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
     * Init all entities from db by {@link EntityDescription}
     */
    private void initializeEntities() {
        @SuppressWarnings("unchecked")
        List<EntityDescription> entities = dao.getAll(EntityDescription.entityName);
        for (EntityDescription description : entities) {
            EntityBlank entityBlank = mappingService.mapping(description.getDescription(), EntityBlank.class);
            Class entity = buildEntity(entityBlank);
            getEntitiesBlank().put(entityBlank.getCode(), entityBlank);
            getEntities().put(entityBlank.getCode(), entity);
        }
        reloadService();
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


}
