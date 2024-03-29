package test_core.factories;

import com.google.common.collect.Maps;
import core.entity.EntityClass;
import core.entity.field.EntityField;

import java.util.Map;

public class ObjectFactory {

    public static Map<String, Object> createObjectParam(EntityClass entityClass) {
        Map<String, Object> params = Maps.newHashMap();
        entityClass.getFields().forEach(entityField ->
                params.put(entityField.getCode(), createValueForField(entityField)));
        return params;
    }

    public static Object createValueForField(EntityField entityField) {
        return EntityFieldFactory.valueEntityField(entityField).apply(entityField);
    }
}