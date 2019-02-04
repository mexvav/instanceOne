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

    @Autowired
    EntityService entityService;

    @Autowired
    SessionFactory sessionFactory;

    /**
     * Exception for object service
     */
    static private class ObjectServiceException extends RuntimeException {
        enum ExceptionCauses {
            ATTRIBUTE_IS_NOT_EXIST("Entity '%s' is not contains attribute '%s'");

            private String cause;

            ExceptionCauses(String cause) {
                this.cause = cause;
            }

            public String getCause() {
                return cause;
            }
        }

        ObjectServiceException(String message) {
            super(message);
        }

        ObjectServiceException(ExceptionCauses cause) {
            super(cause.getCause());
        }

        ObjectServiceException(ExceptionCauses cause, String... args) {
            super(String.format(cause.getCause(), (Object[]) args));
        }
    }

    @Transactional
    public void createObject(String code, Map<String, Object> params){
        EntityBlank entityBlank = entityService.getEntityBlank(code);
        Class entityClass = entityService.getEntity(code);
        Object entityInstance;
        try {
            entityInstance = entityClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e){
            throw new ObjectServiceException(e.getMessage());
        }

        Collection<Attribute> attributes = entityBlank.getAttributes();

        for(Map.Entry<String, Object> param : params.entrySet()){
            Attribute attribute = attributes.stream().filter(attr->attr.getCode().equals(param.getKey())).findFirst().orElse(null);
            if(null == attribute){
                throw new ObjectServiceException(ObjectServiceException.ExceptionCauses.ATTRIBUTE_IS_NOT_EXIST, code, param.getKey());
            }
        }

        for(Attribute attribute : attributes){
            if(!params.containsKey(attribute.getCode())){
                continue;
            }

            //TODO make Attribute Mappers
            Object value = params.get(attribute.getCode());
            if(attribute.getType().getAttributeClass().equals(value.getClass())){
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
