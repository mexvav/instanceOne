package core.factories;

import com.google.common.collect.Maps;
import core.jpa.entity.EntityClass;
import core.jpa.entity.fields.EntityField;

import java.util.Map;

public class ObjectFactory {

    public static Map<String, Object> createObjectParam(EntityClass entityClass) {
        Map<String, Object> params = Maps.newHashMap();
        entityClass.getFields().forEach(entityField ->
                params.put(entityField.getCode(), createValueForField(entityField)));
        return params;
    }

    public static Object createValueForField(EntityField entityField) {
        return EntityFieldFactory.valueEntityField(entityField);
    }
}