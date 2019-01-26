package core.jpa;

import com.google.common.collect.Maps;
import core.Constants;
import core.jpa.attribute.Attribute;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;


@Component
public class EntityService {
    private ReloadableSessionFactory sessionFactory;
    private Map<String, Class<?>> entities;
    private EntityBuilder entityBuilder;

    EntityService(SessionFactory sessionFactory, EntityBuilder entityBuilder) {
        if (sessionFactory instanceof ReloadableSessionFactory) {
            this.sessionFactory = (ReloadableSessionFactory) sessionFactory;
        } else {
            throw new RuntimeException("SessionFactory is not reloadable");
        }
        this.entityBuilder = entityBuilder;
    }

    private Map<String, Class<?>> getEntities() {
        if (null == entities) {
            entities = Maps.newHashMap();
        }
        return entities;
    }

    public void createEntity(String name, Collection<Attribute> attributes) {
        if (getEntities().containsKey(name)) {
            return;
        }

        EntityBuilder.EntityParam param = new EntityBuilder.EntityParam(name);
        param.setTablePrefix(Constants.dbEntityPrefix);
        param.setAttributes(attributes);
        Class entity = entityBuilder.build(param);

        getEntities().put(name, entity);
        reloadService();
    }

    public void reloadService() {
        sessionFactory.getEntities().addAll(entities.values());
        sessionFactory.reloadSessionFactory();
    }
}
