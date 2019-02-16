package core.jpa.object;

import core.jpa.entity.EntityClass;
import core.jpa.entity.EntityService;
import core.jpa.entity.fields.EntityField;
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
        EntityClass entityBlank = entityService.getEntityBlank(code);
        Class entityClass = entityService.getEntity(code);
        Object entityInstance;
        try {
            entityInstance = entityClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ObjectServiceException(e.getMessage());
        }

        Collection<EntityField> entityFields = entityBlank.getFields();

        for (Map.Entry<String, Object> param : params.entrySet()) {
            EntityField entityField =
                    entityFields.stream()
                            .filter(attr -> attr.getCode().equals(param.getKey()))
                            .findFirst()
                            .orElse(null);
            if (null == entityField) {
                throw new ObjectServiceException(
                        ObjectServiceException.ExceptionCauses.ATTRIBUTE_IS_NOT_EXIST, code, param.getKey());
            }
        }

        for (EntityField entityField : entityFields) {
            if (!params.containsKey(entityField.getCode())) {
                continue;
            }

            Object value = params.get(entityField.getCode());
            if (entityField.getType().getFieldClass().equals(value.getClass())) {
                try {
                    Field field = entityClass.getDeclaredField(entityField.getCode());
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
