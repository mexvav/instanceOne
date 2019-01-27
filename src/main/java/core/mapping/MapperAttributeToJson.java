package core.mapping;

import core.jpa.EntityBlank;
import core.jpa.attribute.Attribute;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class MapperAttributeToJson extends MapperAbstract<Attribute, String> {

    @Override
    public Class<Attribute> getFromClass(){
        return Attribute.class;
    }

    @Override
    public Class<String> getToClass(){
        return String.class;
    }

    @Override
    public String transform(Attribute from) {
        Map<String, Object> transition = getMappingService().mapping(from, Map.class);
        return getMappingService().mapping(transition, String.class);
    }
}