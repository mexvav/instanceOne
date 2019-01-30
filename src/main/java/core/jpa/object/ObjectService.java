package core.jpa.object;

import core.jpa.EntityBlank;
import core.jpa.EntityService;
import core.jpa.attribute.Attribute;
import core.jpa.entity.EntityDescription;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

@Repository("dao")
@Transactional
public class ObjectService {

    @Autowired
    EntityService entityService;

    @Autowired
    SessionFactory sessionFactory;

    public void createObject(String code, Map<String, Object> params){
        EntityBlank entityBlank = entityService.getEntityBlank(code);
        Class entityClass = entityService.getEntity(code);
        Object entityInstance;
        try {
            entityInstance = entityClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e){
            throw new RuntimeException(e.getMessage());
        }


        Collection<Attribute> attributes = entityBlank.getAttributes();
        for(Attribute attribute : attributes){
            if(!params.containsKey(attribute.getCode())){
                continue;
            }

            //TODO make Attribute Mappers
            Object value = params.get(attribute.getCode());
            if(!attribute.getType().getAttributeClass().equals(value.getClass())){
                try {
                    Field field = entityClass.getField(code);
                    field.setAccessible(true);
                    field.set(entityInstance, value);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        }

        Session session = sessionFactory.openSession();
        session.save(entityInstance);
        session.close();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void test(){
        EntityDescription entityDescription = new EntityDescription();
        entityDescription.setName("1");
        entityDescription.setDescription("test");

        sessionFactory.getCurrentSession().persist(entityDescription);
    }


}
