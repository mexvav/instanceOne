package test_core.assertions;

import core.entity.entities.Entity;
import core.object.processing.DataObject;
import org.junit.jupiter.api.Assertions;
import test_core.utils.SpringContextUtils;

import java.util.Map;

public class ObjectAssertions {

    private SpringContextUtils utils;

    ObjectAssertions(SpringContextUtils utils) {
        this.utils = utils;
    }

    public static void assertEquals(Map<String, Object> expect, Map<String, Object> actual) {
        expect.forEach((key, value) ->
                Assertions.assertEquals(value, actual.get(key)));
    }

    public static void assertEquals(Map<String, Object> expect, DataObject actual) {
        assertEquals(expect, actual.getProperties());
    }

    public static void assertEquals(DataObject expect, DataObject actual) {
        assertEquals(expect.getProperties(), actual.getProperties());
    }

    public void assertExist(DataObject object) {
        assertExist(object.getEntityCode(), object.getId());
    }

    public void assertExist(String entityCode, Long id) {
        Class<? extends Entity> entity = utils.getEntityService().getEntity(entityCode);
        Assertions.assertNotNull(utils.getObjectDAO().get(entity, id));
    }

    public void assertNotExist(DataObject object) {
        assertNotExist(object.getEntityCode(), object.getId());
    }

    public void assertNotExist(String entityCode, Long id) {
        Class<? extends Entity> entity = utils.getEntityService().getEntity(entityCode);
        Assertions.assertNull(utils.getObjectDAO().get(entity, id));
    }
}
