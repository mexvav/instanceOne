package core.jpa.mapping.mappers;

import core.jpa.entity.attribute.type.AttributeSimpleType;

import java.util.Map;

public class MapperSimpleAttributeTypeToJson extends MapperAbstract<AttributeSimpleType, String> {

    @Override
    public Class<AttributeSimpleType> getFromClass() {
        return AttributeSimpleType.class;
    }

    @Override
    public Class<String> getToClass() {
        return String.class;
    }

    @Override
    public String transform(AttributeSimpleType from) {
        Map transition = getMappingService().mapping(from, Map.class);
        return getMappingService().mapping(transition, String.class);
    }
}