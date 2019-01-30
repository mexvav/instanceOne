package core.jpa;

import com.google.common.collect.Maps;
import core.jpa.entity_builder.BuilderService;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EntityService {
    private ReloadableSessionFactory sessionFactory;
    private Map<String, Class<?>> entities;
    private Map<String, EntityBlank> entitiesBlank;
    private BuilderService builderService;

    EntityService(SessionFactory sessionFactory, BuilderService builderService) {
        if (sessionFactory instanceof ReloadableSessionFactory) {
            this.sessionFactory = (ReloadableSessionFactory) sessionFactory;
        } else {
            throw new RuntimeException("SessionFactory is not reloadable");
        }
        this.builderService = builderService;
    }

    /**
     * Create new entity in runtime
     * @param entityBlank - entity blank
     */
    public void createEntity(EntityBlank entityBlank) {
        if (getEntities().containsKey(entityBlank.getCode())) {
            return;
        }

        Class entity = builderService.buildEntity(entityBlank);
        getEntities().put(entityBlank.getCode(), entity);
        getEntitiesBlank().put(entityBlank.getCode(), entityBlank);

        try{
            reloadService();
        }catch (Exception e){
            getEntities().remove(entityBlank.getCode());
            getEntitiesBlank().remove(entityBlank.getCode());
        }
    }

    /**
     * Rebuild session factory, load new entity
     */
    public void reloadService() {
        sessionFactory.getEntities().addAll(entities.values());
        sessionFactory.reloadSessionFactory();
    }

    public EntityBlank getEntityBlank(String code){
        return getEntitiesBlank().get(code);
    }

    public Class<?> getEntity(String code){
        return getEntities().get(code);
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

    private Map<String,EntityBlank> getEntitiesBlank(){
        if (null == entitiesBlank) {
            entitiesBlank = Maps.newHashMap();
        }
        return entitiesBlank;
    }

}
