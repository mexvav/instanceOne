package core.jpa.object;

import com.google.common.collect.Sets;
import core.jpa.entity.EntityClass;
import core.jpa.entity.EntityService;
import core.jpa.entity.field.EntityField;
import core.jpa.object.resolving.resolvers.EntityFieldValueResolver;
import core.utils.ReflectionUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Repository
@Transactional
public class ObjectService {

    private EntityService entityService;
    private SessionFactory sessionFactory;
    private Set<EntityFieldValueResolver> valueResolvers;

    @Autowired
    ObjectService(EntityService entityService, SessionFactory sessionFactory) {
        this.entityService = entityService;
        this.sessionFactory = sessionFactory;
        initResolvers();
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
            EntityField entityField = entityFields.stream()
                    .filter(attr -> attr.getCode().equals(param.getKey()))
                    .findFirst()
                    .orElseThrow(() ->
                            new ObjectServiceException(
                                    ObjectServiceException.ExceptionCauses.ATTRIBUTE_IS_NOT_EXIST, code, param.getKey()));


            /*
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
*/

        }
        sessionFactory.getCurrentSession().persist(entityInstance);
    }

    private void setValues(EntityClass entityBlank, Map<String, Object> params) {

        Collection<EntityField> entityFields = entityBlank.getFields();

        for (Map.Entry<String, Object> param : params.entrySet()) {
            EntityField entityField = entityFields.stream()
                    .filter(attr -> attr.getCode().equals(param.getKey()))
                    .findFirst().orElseThrow(() -> new ObjectServiceException(
                            ObjectServiceException.ExceptionCauses.ATTRIBUTE_IS_NOT_EXIST,
                            entityBlank.getCode(), param.getKey()));

        }
    }

    private void initResolvers() {
        ReflectionUtils.actionWithSubTypes(
                EntityFieldValueResolver.class.getPackage().getName(),
                EntityFieldValueResolver.class,
                resolverClass -> {
                    try {
                        getValueResolvers().add(resolverClass.newInstance());
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    private Set<EntityFieldValueResolver> getValueResolvers() {
        if (null == valueResolvers) {
            valueResolvers = Sets.newHashSet();
        }
        return valueResolvers;
    }
}
