package core.jpa.mapping.mappers;

import core.jpa.Constants;
import core.jpa.entity.attribute.Attribute;
import core.jpa.entity.attribute.type.AttributeType;

import java.util.Map;

public class MapperMapToAttribute extends MapperAbstract<Map, Attribute> {

    @Override
    public Class<Map> getFromClass() {
        return Map.class;
    }

    @Override
    public Class<Attribute> getToClass() {
        return Attribute.class;
    }

    @Override
    public Attribute transform(Map from) {
        Map typeMap = (Map) from.get(Constants.Attribute.TYPE);
        AttributeType type = getMappingService().mapping(typeMap, AttributeType.class);
        Attribute attribute = new Attribute((String) from.get(Constants.HasCode.CODE));
        attribute.setType(type);

        return attribute;
    }
}