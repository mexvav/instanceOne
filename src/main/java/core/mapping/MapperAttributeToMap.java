package core.mapping;

import com.google.common.collect.Maps;
import core.jpa.EntityBlank;
import core.jpa.attribute.Attribute;
import core.jpa.attribute.type.AttributeType;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
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
        transition.put("code", from.getCode());
        transition.put("required", from.isRequired());
        transition.put("unique", from.isUnique());
        transition.put("type", typeJson);

        return transition;
    }
}