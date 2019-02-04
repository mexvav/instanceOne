package core.jpa.entity;

import com.google.common.collect.Maps;
import core.jpa.ReloadableSessionFactory;
import core.jpa.dao.DAOEntity;
import core.jpa.entity.entities.EntityDescription;
import core.jpa.entity.building.BuilderService;
import core.jpa.mapping.MappingService;
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
    private BuilderService builderService;
    private MappingService mappingService;
    private DAOEntity dao;

    @Autowired
    EntityService(SessionFactory sessionFactory, BuilderService builderService,
                  MappingService mappingService, DAOEntity dao) {
        if (sessionFactory instanceof ReloadableSessionFactory) {
            this.sessionFactory = (ReloadableSessionFactory) sessionFactory;
        } else {
            throw new EntityServiceException(EntityServiceException.ExceptionCauses.SESSION_FACTORY_IS_NOT_RELOADABLE);
        }
        this.builderService = builderService;
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

        Class entity = builderService.buildEntity(entityBlank);
        getEntities().put(entityBlank.getCode(), entity);
        getEntitiesBlank().put(entityBlank.getCode(), entityBlank);
        try {
            reloadService();
            saveDescription(entityBlank);
        } catch (Exception e) {
            getEntities().remove(entityBlank.getCode());
            getEntitiesBlank().remove(entityBlank.getCode());
            throw new EntityServiceException(EntityService.EntityServiceException.ExceptionCauses.SERVICE_RELOAD_ERROR, e.getMessage());
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
            Class entity = builderService.buildEntity(entityBlank);
            getEntitiesBlank().put(entityBlank.getCode(), entityBlank);
            getEntities().put(entityBlank.getCode(), entity);
        }
        reloadService();
    }

    /**
     * Get created in runtime entities
     */
    private Map<String, Class<?>> getEntities() {
        if (null == entities) {
            entities = Maps.newHashMap();
        }
        return entities;
    }

    private Map<String, EntityBlank> getEntitiesBlank() {
        if (null == entitiesBlank) {
            entitiesBlank = Maps.newHashMap();
        }
        return entitiesBlank;
    }

    /**
     * Exception for entity builder
     */
    static private class EntityServiceException extends RuntimeException {
        enum ExceptionCauses {
            ENTITY_IS_NOT_EXIST("Entity by code '%s' is not exist"),
            SERVICE_RELOAD_ERROR("Entity service can't reload, cause: %s"),
            SESSION_FACTORY_IS_NOT_RELOADABLE("SessionFactory is not reloadable");

            public String getCause() {
                return cause;
            }

            private String cause;

            ExceptionCauses(String cause) {
                this.cause = cause;
            }
        }

        EntityServiceException(String message) {
            super(message);
        }

        EntityServiceException(ExceptionCauses cause) {
            super(cause.getCause());
        }

        EntityServiceException(ExceptionCauses cause, String... args) {
            super(String.format(cause.getCause(), (Object[]) args));
        }
    }
}
