package core.mapping;

import core.jpa.attribute.type.AttributeSimpleType;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
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