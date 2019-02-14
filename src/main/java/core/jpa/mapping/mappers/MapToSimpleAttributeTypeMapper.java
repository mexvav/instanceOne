package core.jpa.mapping.mappers;

import core.jpa.entity.fields.types.SimpleEntityFieldType;

import java.util.Map;

public class MapToSimpleAttributeTypeMapper extends AbstractMapper<Map, SimpleEntityFieldType> {

    @Override
    public Class<Map> getFromClass() {
        return Map.class;
    }

    @Override
    public Class<SimpleEntityFieldType> getToClass() {
        return SimpleEntityFieldType.class;
    }

    @Override
    public SimpleEntityFieldType transform(Map from) {
        return (SimpleEntityFieldType) from.get("instance");
    }
}
