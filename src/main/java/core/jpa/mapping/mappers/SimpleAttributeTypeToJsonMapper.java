package core.jpa.mapping.mappers;

import core.jpa.entity.fields.types.SimpleEntityFieldType;

import java.util.Map;

public class SimpleAttributeTypeToJsonMapper extends AbstractMapper<SimpleEntityFieldType, String> {

    @Override
    public Class<SimpleEntityFieldType> getFromClass() {
        return SimpleEntityFieldType.class;
    }

    @Override
    public Class<String> getToClass() {
        return String.class;
    }

    @Override
    public String transform(SimpleEntityFieldType from) {
        Map transition = getMappingService().mapping(from, Map.class);
        return getMappingService().mapping(transition, String.class);
    }
}
