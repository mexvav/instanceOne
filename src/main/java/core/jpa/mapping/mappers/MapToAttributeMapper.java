package core.jpa.mapping.mappers;

import core.jpa.Constants;
import core.jpa.entity.fields.EntityField;
import core.jpa.entity.fields.types.EntityFieldType;

import java.util.Map;

public class MapToAttributeMapper extends AbstractMapper<Map, EntityField> {

    @Override
    public Class<Map> getFromClass() {
        return Map.class;
    }

    @Override
    public Class<EntityField> getToClass() {
        return EntityField.class;
    }

    @Override
    public EntityField transform(Map from) {
        Map typeMap = (Map) from.get(Constants.Attribute.TYPE);
        EntityFieldType type = getMappingService().mapping(typeMap, EntityFieldType.class);
        EntityField entityField = new EntityField((String) from.get(Constants.HasCode.CODE));
        entityField.setType(type);

        return entityField;
    }
}
