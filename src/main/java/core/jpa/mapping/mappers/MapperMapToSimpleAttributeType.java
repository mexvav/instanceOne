package core.jpa.mapping.mappers;

import core.jpa.entity.attribute.type.AttributeSimpleType;

import java.util.Map;

public class MapperMapToSimpleAttributeType extends MapperAbstract<Map, AttributeSimpleType> {

    @Override
    public Class<Map> getFromClass(){
        return Map.class;
    }

    @Override
    public Class<AttributeSimpleType> getToClass(){
        return AttributeSimpleType.class;
    }

    @Override
    public AttributeSimpleType transform(Map from) {
        return (AttributeSimpleType)from.get("instance");
    }
}