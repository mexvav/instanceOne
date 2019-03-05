package core.object;

import com.google.common.collect.Maps;
import core.entity.EntityClass;
import core.entity.field.EntityField;
import core.object.processing.DataObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test_core.AbstractSpringContextTest;
import test_core.assertions.ObjectAssertions;
import test_core.factories.EntityClassFactory;
import test_core.factories.EntityFieldFactory;
import test_core.factories.ObjectFactory;
import test_core.utils.SpringContextUtils;

import java.util.Map;

class ObjectServiceTest extends AbstractSpringContextTest {

    private SpringContextUtils utils;

    @Autowired
    ObjectServiceTest(SpringContextUtils utils) {
        this.utils = utils;
    }

    /**
     * Testing {@link ObjectService#create(String, Map)}
     */
    @Test
    void testCreateObject() {
        //create entity
        EntityClass entityClass = EntityClassFactory.create();
        EntityFieldFactory.setAllFields(entityClass);

        utils.getEntityService().createEntity(entityClass);
        utils.assertions().entity().assertExist(entityClass);

        //create object
        Map<String, Object> params = ObjectFactory.createObjectParam(entityClass);
        DataObject object = utils.getObjectService().create(entityClass.getCode(), params);

        //check object
        utils.assertions().object().assertExist(object);
        ObjectAssertions.assertEquals(params, object);
    }

    /**
     * Testing {@link ObjectService#get(String, long)}
     */
    @Test
    void testGetObject() {
        //create entity
        EntityClass entityClass = EntityClassFactory.create();
        EntityFieldFactory.setAllFields(entityClass);

        utils.getEntityService().createEntity(entityClass);
        utils.assertions().entity().assertExist(entityClass);

        //create object
        Map<String, Object> params = ObjectFactory.createObjectParam(entityClass);
        DataObject object = utils.getObjectService().create(entityClass.getCode(), params);

        //getting object
        DataObject actionObject = utils.getObjectService().get(entityClass.getCode(), object.getId());

        //check object
        ObjectAssertions.assertEquals(params, actionObject);
    }

    /**
     * Testing {@link ObjectService#remove(DataObject)}}
     */
    @Test
    void testRemoveObject() {
        //create entity
        EntityClass entityClass = EntityClassFactory.create();
        EntityFieldFactory.setAllFields(entityClass);

        utils.getEntityService().createEntity(entityClass);
        utils.assertions().entity().assertExist(entityClass);

        //create object
        Map<String, Object> params = ObjectFactory.createObjectParam(entityClass);
        DataObject object = utils.getObjectService().create(entityClass.getCode(), params);
        utils.assertions().object().assertExist(object);

        //remove object
        utils.getObjectService().remove(object.getEntityCode(), object.getId());
        utils.assertions().object().assertNotExist(object);
    }

    /**
     * Testing {@link ObjectService#edit(DataObject, Map)}}
     */
    @Test
    void testEditObject() {
        //create entity
        EntityClass entityClass = EntityClassFactory.create();
        EntityFieldFactory.setAllFields(entityClass);

        utils.getEntityService().createEntity(entityClass);
        utils.assertions().entity().assertExist(entityClass);

        //create object
        Map<String, Object> params = ObjectFactory.createObjectParam(entityClass);
        DataObject object = utils.getObjectService().create(entityClass.getCode(), params);

        utils.assertions().object().assertExist(object);
        ObjectAssertions.assertEquals(params, object);

        //edit object
        Map<String, Object> editParams = Maps.newHashMap(params);
        EntityField field = entityClass.getFields().iterator().next();
        editParams.put(field.getCode(), ObjectFactory.createValueForField(field));

        //check object
        DataObject editObject = utils.getObjectService().edit(object, editParams);
        ObjectAssertions.assertEquals(editParams, editObject);
    }
}
