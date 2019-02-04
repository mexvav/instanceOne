package core.jpa.mapping.mappers;

import com.google.common.collect.Maps;
import core.jpa.Constants;
import core.jpa.entity.attribute.type.AttributeSimpleType;
import java.util.Map;

public class MapperSimpleAttributeTypeToMap extends MapperAbstract<AttributeSimpleType, Map> {

    @Override
    public Class<AttributeSimpleType> getFromClass(){
        return AttributeSimpleType.class;
    }

    @Override
    public Class<Map> getToClass(){
        return Map.class;
    }

    @Override
    public Map transform(AttributeSimpleType from) {
        Map<String, Object> transition = Maps.newHashMap();
        transition.put(Constants.HasCode.CODE, from.getCode());
        return transition;
    }
}