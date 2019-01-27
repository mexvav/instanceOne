package core.mapping;

import core.jpa.EntityBlank;
import core.jpa.attribute.Attribute;
import core.jpa.attribute.type.AttributeType;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MapperMapToAttribute extends MapperAbstract<Map, Attribute> {

    @Override
    public Class<Map> getFromClass(){
        return Map.class;
    }

    @Override
    public Class<Attribute> getToClass(){
        return Attribute.class;
    }

    @Override
    public Attribute transform(Map from) {
        Map typeMap = (Map) from.get("type");
        AttributeType type = getMappingService().mapping(typeMap, AttributeType.class);

        Attribute attribute = new Attribute((String) from.get("code"));
        attribute.setType(type);

        return attribute;
    }
}