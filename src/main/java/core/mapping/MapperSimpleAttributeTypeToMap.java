package core.mapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import core.jpa.EntityBlank;
import core.jpa.attribute.type.AttributeSimpleType;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
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
        transition.put("code", from.getCode());
        return transition;
    }
}