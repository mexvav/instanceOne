package core.object;

import com.google.common.collect.Maps;
import core.dao.DbDAO;
import core.dao.ObjectDAO;
import core.entity.EntityClass;
import core.entity.EntityService;
import core.entity.field.EntityField;
import core.object.processing.ResultObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test_core.SpringContextAbstractTest;
import test_core.assertions.ObjectAssertions;
import test_core.factories.EntityClassFactory;
import test_core.factories.EntityFieldFactory;
import test_core.factories.ObjectFactory;

import java.util.Map;

class ObjectServiceTest extends SpringContextAbstractTest {

    @Autowired
    ObjectServiceTest(EntityService entityService,
                      ObjectService objectService,
                      DbDAO dbDAO,
                      ObjectDAO objectDAO) {
        super(entityService, objectService, dbDAO, objectDAO);
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
        ResultObject object = utils.getObjectService().create(entityClass.getCode(), params);

        //check object
        utils.assertions().object().assertExist(object);
        ObjectAssertions.assertEquals(params, object);
    }

    /**
     * Testing {@link ObjectService#remove(Object)}}
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
        ResultObject object = utils.getObjectService().create(entityClass.getCode(), params);
        utils.assertions().object().assertExist(object);

        //remove object
        utils.getObjectService().remove(object.getEntityCode(), object.getId());
        utils.assertions().object().assertNotExist(object);
    }

    /**
     * Testing {@link ObjectService#edit(ResultObject, Map)}}
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
        ResultObject object = utils.getObjectService().create(entityClass.getCode(), params);

        utils.assertions().object().assertExist(object);
        ObjectAssertions.assertEquals(params, object);

        //edit object
        Map<String, Object> editParams = Maps.newHashMap(params);
        EntityField field = entityClass.getFields().iterator().next();
        editParams.put(field.getCode(), ObjectFactory.createValueForField(field));

        //check object
        ResultObject editObject = utils.getObjectService().edit(object, editParams);
        ObjectAssertions.assertEquals(editParams, editObject);
    }
}
