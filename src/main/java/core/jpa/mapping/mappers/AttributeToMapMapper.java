package core.jpa.mapping.mappers;

import com.google.common.collect.Maps;
import core.jpa.Constants;
import core.jpa.entity.fields.EntityField;
import core.jpa.entity.fields.types.EntityFieldType;

import java.util.Map;

public class AttributeToMapMapper extends AbstractMapper<EntityField, Map> {

    @Override
    public Class<EntityField> getFromClass() {
        return EntityField.class;
    }

    @Override
    public Class<Map> getToClass() {
        return Map.class;
    }

    @Override
    public Map transform(EntityField from) {
        EntityFieldType type = from.getType();
        Map typeJson = getMappingService().mapping(type, Map.class);

        Map<String, Object> transition = Maps.newHashMap();
        transition.put(Constants.HasCode.CODE, from.getCode());
        transition.put(Constants.Attribute.REQUIRED, from.isRequired());
        transition.put(Constants.Attribute.UNIQUE, from.isUnique());
        transition.put(Constants.Attribute.TYPE, typeJson);

        return transition;
    }
}
