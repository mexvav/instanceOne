package test_core.utils;

import core.entity.EntityClass;
import core.entity.field.EntityField;
import core.object.processing.DataObject;
import test_core.factories.EntityClassFactory;

import java.util.Map;

public class SpringContextCreator {

    private SpringContextUtils utils;

    public SpringContextCreator(SpringContextUtils utils){
        this.utils = utils;
    }

    public EntityClass entity(){
        EntityClass entityClass = EntityClassFactory.create();
        return entity(entityClass);
    }

    public EntityClass entity(EntityField... fields){
        EntityClass entityClass = EntityClassFactory.create();
        return entity(entityClass, fields);
    }

    public EntityClass entity(EntityClass entityClass, EntityField... fields){
        entityClass.addFields(fields);
        return entity(entityClass);
    }

    public EntityClass entity(EntityClass entityClass){
        utils.getEntityService().createEntity(entityClass);
        return entityClass;
    }

    public DataObject object(EntityClass entityClass, Map<String, Object> params){
        return object(entityClass.getCode(), params);
    }

    public DataObject object(String entityCode, Map<String, Object> params){
        return utils.getObjectService().create(entityCode, params);
    }
}
