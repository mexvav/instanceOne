package core.jpa.object;

import com.google.common.collect.Maps;
import core.jpa.entity.EntityClass;
import core.jpa.entity.EntityService;
import core.jpa.entity.field.EntityField;
import core.jpa.object.resolving.ResolvingService;
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
    private ResolvingService resolvingService;


    @Autowired
    ObjectService(EntityService entityService, SessionFactory sessionFactory, ResolvingService resolvingService) {
        this.entityService = entityService;
        this.sessionFactory = sessionFactory;
        this.resolvingService = resolvingService;
    }

    @Transactional
    public void createObject(String code, Map<String, Object> params) {
        EntityClass entityBlank = entityService.getEntityBlank(code);
        Class entityClass = entityService.getEntity(entityBlank.getCode());

        Object entityInstance;
        try {
            entityInstance = entityClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ObjectServiceException(e);
        }

        Map<String, Object> values = getValues(entityBlank, params);
        setValues(entityInstance, values);

        sessionFactory.getCurrentSession().persist(entityInstance);
    }

    private Map<String, Object> getValues(EntityClass entityBlank, Map<String, Object> params) {
        Map<String, Object> values = Maps.newHashMap();

        Collection<EntityField> entityFields = entityBlank.getFields();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            EntityField entityField = entityFields.stream()
                    .filter(attr -> attr.getCode().equals(param.getKey()))
                    .findFirst().orElseThrow(() -> new ObjectServiceException(
                            ObjectServiceException.ExceptionCauses.FIELD_IS_NOT_EXIST,
                            entityBlank.getCode(), param.getKey()));
            Object value = resolvingService.resolve(entityField, param.getValue());
            values.put(entityField.getCode(), value);
        }
        return values;
    }

    private void setValues(Object entityInstance, Map<String, Object> values) {
        Class entityClass = entityInstance.getClass();
        for (Map.Entry<String, Object> param : values.entrySet()) {
            try {
                Field field = entityClass.getDeclaredField(param.getKey());
                field.setAccessible(true);
                field.set(entityInstance, param.getValue());
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new ObjectServiceException(e);
            }
        }
    }


}
