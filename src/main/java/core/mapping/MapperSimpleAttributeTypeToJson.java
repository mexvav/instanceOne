package core.mapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import core.jpa.EntityBlank;
import core.jpa.attribute.type.AttributeSimpleType;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MapperSimpleAttributeTypeToJson extends MapperAbstract<AttributeSimpleType, String> {

    @Override
    public Class<AttributeSimpleType> getFromClass(){
        return AttributeSimpleType.class;
    }

    @Override
    public Class<String> getToClass(){
        return String.class;
    }

    @Override
    public String transform(AttributeSimpleType from) {
        Map<String, Object> transition = getMappingService().mapping(from, Map.class);
        return getMappingService().mapping(transition, String.class);
    }
}