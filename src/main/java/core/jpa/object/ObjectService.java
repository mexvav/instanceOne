package core.jpa.object;

import core.jpa.entity.EntityBlank;
import core.jpa.entity.EntityService;
import core.jpa.entity.attribute.Attribute;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

@Repository
@Transactional
public class ObjectService {

    private EntityService entityService;
    private SessionFactory sessionFactory;

    @Autowired
    ObjectService(EntityService entityService, SessionFactory sessionFactory) {
        this.entityService = entityService;
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void createObject(String code, Map<String, Object> params) {
        EntityBlank entityBlank = entityService.getEntityBlank(code);
        Class entityClass = entityService.getEntity(code);
        Object entityInstance;
        try {
            entityInstance = entityClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ObjectServiceException(e.getMessage());
        }

        Collection<Attribute> attributes = entityBlank.getAttributes();

        for (Map.Entry<String, Object> param : params.entrySet()) {
            Attribute attribute = attributes.stream().filter(attr -> attr.getCode().equals(param.getKey())).findFirst().orElse(null);
            if (null == attribute) {
                throw new ObjectServiceException(ObjectServiceException.ExceptionCauses.ATTRIBUTE_IS_NOT_EXIST, code, param.getKey());
            }
        }

        for (Attribute attribute : attributes) {
            if (!params.containsKey(attribute.getCode())) {
                continue;
            }

            Object value = params.get(attribute.getCode());
            if (attribute.getType().getAttributeClass().equals(value.getClass())) {
                try {
                    Field field = entityClass.getDeclaredField(attribute.getCode());
                    field.setAccessible(true);
                    field.set(entityInstance, value);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        }

        sessionFactory.getCurrentSession().persist(entityInstance);
    }
}
