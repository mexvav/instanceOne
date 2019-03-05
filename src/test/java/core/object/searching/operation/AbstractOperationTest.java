package core.object.searching.operation;

import com.google.common.collect.Maps;
import core.entity.EntityClass;
import core.entity.field.EntityField;
import core.object.processing.DataObject;
import org.junit.jupiter.api.Assertions;
import test_core.AbstractSpringContextTest;
import test_core.assertions.ObjectAssertions;
import test_core.utils.SpringContextUtils;

import java.util.List;
import java.util.Map;

abstract class AbstractOperationTest extends AbstractSpringContextTest {

    private SpringContextUtils utils;

    AbstractOperationTest(SpringContextUtils utils) {
        this.utils = utils;
    }

    DataObject createObject(EntityClass entityClass, EntityField field, Object value) {
        Map<String, Object> params = Maps.newHashMap();
        params.put(field.getCode(), value);
        return utils.create().object(entityClass, params);
    }

    List<DataObject> search(EntityClass entityClass, EntityField field, Object searchingValue) {
        Map<String, Object> searchParams = Maps.newHashMap();
        searchParams.put(field.getCode(), searchingValue);
        return utils.getObjectService().search(entityClass.getCode(), searchParams);
    }

    void assertObjectFound(EntityField field, Object value, Object searchingValue) {
        EntityClass entityClass = utils.create().entity(field);
        DataObject object = createObject(entityClass, field, value);
        List<DataObject> objects = search(entityClass, field, searchingValue);

        Assertions.assertFalse(objects.isEmpty(), "Object is must be found");
        ObjectAssertions.assertEquals(object, objects.iterator().next());
    }

    void assertObjectNotFound(EntityField field, Object value, Object searchingValue) {
        EntityClass entityClass = utils.create().entity(field);
        DataObject object = createObject(entityClass, field, value);
        List<DataObject> objects = search(entityClass, field, searchingValue);

        Assertions.assertEquals(0, objects.size(), "Object is must not be found");
    }
}
