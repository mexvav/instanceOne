package core.jpa.mapping.mappers;

import core.jpa.entity.fields.EntityField;

import java.util.Map;

public class AttributeToJsonMapper extends AbstractMapper<EntityField, String> {

    @Override
    public Class<EntityField> getFromClass() {
        return EntityField.class;
    }

    @Override
    public Class<String> getToClass() {
        return String.class;
    }

    @Override
    public String transform(EntityField from) {
        Map transition = getMappingService().mapping(from, Map.class);
        return getMappingService().mapping(transition, String.class);
    }
}
