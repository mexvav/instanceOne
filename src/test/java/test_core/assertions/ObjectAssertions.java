package test_core.assertions;

import core.object.processing.ResultObject;
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

    public static void assertEquals(Map<String, Object> expect, ResultObject actual) {
        assertEquals(expect, actual.getValues());
    }

    public void assertExist(ResultObject object) {
        assertExist(object.getEntityCode(), object.getId());
    }

    public void assertExist(String entityCode, Long id) {
        Class entity = utils.getEntityService().getEntity(entityCode);
        Assertions.assertNotNull(utils.getObjectDAO().get(entity, id));
    }

    public void assertNotExist(ResultObject object) {
        assertNotExist(object.getEntityCode(), object.getId());
    }

    public void assertNotExist(String entityCode, Long id) {
        Class entity = utils.getEntityService().getEntity(entityCode);
        Assertions.assertNull(utils.getObjectDAO().get(entity, id));
    }
}
