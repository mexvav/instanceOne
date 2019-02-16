package core.jpa.entity.fields.types;

import com.google.common.collect.Maps;
import core.jpa.entity.EntityServiceException;
import core.utils.ReflectionUtils;

import java.util.Map;

public class EntityFieldTypeFactoty {

    private static Map<String, Class<? extends EntityFieldType>> entityFieldTypes;

    public static EntityFieldType createByCode(String code) {
        try {
            return getEntityFieldTypeClassByCode(code).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new EntityServiceException(e.getMessage());
        }
    }

    public static Class<? extends EntityFieldType> getEntityFieldTypeClassByCode(String code) {
        if (getEntityFieldTypes().containsKey(code)) {
            return getEntityFieldTypes().get(code);
        }
        throw new EntityServiceException(EntityServiceException.ExceptionCauses.ENTITY_FIELD_TYPE_NOT_FOUND, code);
    }

    private static Map<String, Class<? extends EntityFieldType>> getEntityFieldTypes() {
        if (null == entityFieldTypes) {
            initEntityFieldTypes();
        }
        return entityFieldTypes;
    }

    private static void initEntityFieldTypes() {
        entityFieldTypes = Maps.newHashMap();
        ReflectionUtils.actionWithSubTypes(EntityFieldTypeFactoty.class.getPackage().getName(), EntityFieldType.class,
                entityFieldTypeClass -> {
                    try {
                        EntityFieldType entityFieldType = entityFieldTypeClass.newInstance();
                        entityFieldTypes.put(entityFieldType.getCode(), entityFieldTypeClass);
                    } catch (InstantiationException | IllegalAccessException e) {
                        throw new EntityServiceException(e.getMessage());
                    }
                });
    }
}
