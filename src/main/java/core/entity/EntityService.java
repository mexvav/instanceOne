package core.entity;

import com.google.common.collect.Maps;
import core.Constants;
import core.aspects.WithReloadSessionFactory;
import core.dao.DbDAO;
import core.dao.ObjectDAO;
import core.entity.building.BuildingService;
import core.entity.entities.Entity;
import core.entity.entities.EntityDescription;
import core.mapping.MappingService;
import core.session_factory.ReloadableSessionFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

@Component
public class EntityService {

    private ReloadableSessionFactory sessionFactory;
    private BuildingService buildingService;
    private MappingService mappingService;
    private ObjectDAO objectDAO;
    private DbDAO dbDAO;

    private Map<String, Class<Entity>> currentEntities;
    private Map<String, Class<Entity>> entities;
    private Map<String, EntityClass> entitiesBlank;

    @Autowired
    EntityService(
            SessionFactory sessionFactory,
            BuildingService buildingService,
            MappingService mappingService,
            ObjectDAO objectDAO,
            DbDAO dbDAO) {
        if (sessionFactory instanceof ReloadableSessionFactory) {
            this.sessionFactory = (ReloadableSessionFactory) sessionFactory;
        } else {
            throw new EntityServiceException(
                    EntityServiceException.ExceptionCauses.SESSION_FACTORY_IS_NOT_RELOADABLE);
        }
        this.buildingService = buildingService;
        this.mappingService = mappingService;
        this.objectDAO = objectDAO;
        this.dbDAO = dbDAO;

        reloadCurrentEntities();
    }

    /**
     * Make some actions with reload session factory. Rollback if exception
     *
     * @param action some actions with reload session factory
     */
    public <R> R actionWithReloadSessionFactory(Supplier<R> action) {
        reloadCurrentEntities();
        Map<String, Class<Entity>> entities = getEntities();
        Map<String, EntityClass> entitiesBlank = getEntitiesBlank();
        try {
            R result = action.get();
            reloadService();
            return result;
        } catch (Throwable e) {
            this.entities = entities;
            this.entitiesBlank = entitiesBlank;
            throw new EntityServiceException(
                    EntityServiceException.ExceptionCauses.SERVICE_RELOAD_ERROR, e, e.getMessage());
        }
    }

    /**
     * Rebuild session factory not safety, use method actionWithReloadSessionFactory or annotation
     * {@link WithReloadSessionFactory}
     */
    public void reloadService() {
        getEntities().forEach((code, entity) -> {
            if (!getCurrentEntities().containsKey(code)) {
                sessionFactory.addEntity(entity);
            }
        });

        getCurrentEntities().forEach((code, entity) -> {
            if (!getEntities().containsKey(code)) {
                sessionFactory.removeEntity(entity);
            }
        });

        sessionFactory.reloadSessionFactory();
        reloadCurrentEntities();
    }

    /**
     * Create new entity in runtime
     *
     * @param entityClasses - description objects for entity class
     */
    @SuppressWarnings("unchecked")
    @WithReloadSessionFactory
    public void createEntity(final EntityClass... entityClasses) {
        for (EntityClass entityClass : entityClasses) {
            if (isEntityExist(entityClass.getCode())) {
                continue;
            }
            Class<Entity> entity = (Class<Entity>) buildingService.building(entityClass);
            getEntities().put(entityClass.getCode(), entity);
            getEntitiesBlank().put(entityClass.getCode(), entityClass);
            saveDescription(entityClass);
        }
    }

    /**
     * Delete entity by code
     *
     * @param codes some codes of entity
     */
    @WithReloadSessionFactory
    public void removeEntity(String... codes) {
        Arrays.stream(codes)
                .forEach(
                        code -> {
                            dbDAO.dropTable(code);
                            getEntities().remove(code);
                            getEntitiesBlank().remove(code);
                        });
    }

    /**
     * Warning! deleting all entities
     */
    @WithReloadSessionFactory
    public void hardClean() {
        Set<String> tables = dbDAO.getAllTables();
        tables.remove(Constants.EntityDescription.TABLE);
        tables.forEach(table -> dbDAO.dropTable(table));
        dbDAO.cleanTable(Constants.EntityDescription.ENTITY_NAME);

        getEntities().clear();
        getEntitiesBlank().clear();
    }

    /**
     * Is entity exist
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
    public EntityClass getEntityBlank(String code) {
        if (isEntityExist(code)) {
            return getEntitiesBlank().get(code);
        }
        throw new EntityServiceException(
                EntityServiceException.ExceptionCauses.ENTITY_IS_NOT_EXIST, code);
    }

    /**
     * Get entity
     *
     * @param code code of entity
     * @throws EntityServiceException if entity not found
     */
    public Class<Entity> getEntity(String code) {
        if (isEntityExist(code)) {
            return getEntities().get(code);
        }
        throw new EntityServiceException(
                EntityServiceException.ExceptionCauses.ENTITY_IS_NOT_EXIST, code);
    }

    /**
     * Save information about entityClass in db. See {@link EntityDescription}
     *
     * @param entityClass description object for entity class
     */
    private void saveDescription(EntityClass entityClass) {
        String json = mappingService.mapping(entityClass, String.class);
        EntityDescription description = new EntityDescription();
        description.setCode(entityClass.getCode());
        description.setDescription(json);
        objectDAO.save(description);
    }

    /**
     * Get all entities
     */
    private Map<String, Class<Entity>> getEntities() {
        if (null == entities) {
            entities = Maps.newHashMap();
        }
        return entities;
    }

    /**
     * Get all entityClass
     */
    private Map<String, EntityClass> getEntitiesBlank() {
        if (null == entitiesBlank) {
            entitiesBlank = Maps.newHashMap();
        }
        return entitiesBlank;
    }

    /**
     * Get actual entities, used for {@link #actionWithReloadSessionFactory(Supplier)}
     */
    private Map<String, Class<Entity>> getCurrentEntities() {
        if (null == currentEntities) {
            currentEntities = Maps.newHashMap();
        }
        return currentEntities;
    }

    /**
     * Reload actual entities
     */
    private void reloadCurrentEntities() {
        currentEntities = Maps.newHashMap(getEntities());
    }
}
