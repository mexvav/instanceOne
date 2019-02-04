package core.jpa.mapping.mappers;

import com.google.common.collect.Maps;
import core.jpa.Constants;
import core.jpa.entity.attribute.Attribute;
import core.jpa.entity.attribute.type.AttributeType;

import java.util.Map;

public class MapperAttributeToMap extends MapperAbstract<Attribute, Map> {

    @Override
    public Class<Attribute> getFromClass(){
        return Attribute.class;
    }

    @Override
    public Class<Map> getToClass(){
        return Map.class;
    }

    @Override
    public Map transform(Attribute from) {
        AttributeType type = from.getType();
        Map typeJson = getMappingService().mapping(type, Map.class);

        Map<String, Object> transition = Maps.newHashMap();
        transition.put(Constants.HasCode.CODE, from.getCode());
        transition.put(Constants.Attribute.REQUIRED, from.isRequired());
        transition.put(Constants.Attribute.UNIQUE, from.isUnique());
        transition.put(Constants.Attribute.TYPE, typeJson);

        return transition;
    }
}