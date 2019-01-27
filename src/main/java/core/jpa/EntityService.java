package core.jpa;

import com.google.common.collect.Maps;
import core.jpa.builder.BuilderService;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class EntityService {
    private ReloadableSessionFactory sessionFactory;
    private Map<String, Class<?>> entities;
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
     * Get created in runtime entities
     */
    private Map<String, Class<?>> getEntities() {
        if (null == entities) {
            entities = Maps.newHashMap();
        }
        return entities;
    }

    /**
     * Create new entity in runtime
     * @param entityBlank
     */
    public void createEntity(EntityBlank entityBlank) {
        if (getEntities().containsKey(entityBlank.getCode())) {
            return;
        }
        Class entity = builderService.buildEntity(entityBlank);
        getEntities().put(entityBlank.getCode(), entity);
        reloadService();
    }

    /**
     * Rebuild session factory, load new entity
     */
    public void reloadService() {
        sessionFactory.getEntities().addAll(entities.values());
        sessionFactory.reloadSessionFactory();
    }
}
