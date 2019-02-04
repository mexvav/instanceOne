package core.jpa.mapping.mappers;

import core.jpa.entity.attribute.Attribute;
import java.util.Map;

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
        Map transition = getMappingService().mapping(from, Map.class);
        return getMappingService().mapping(transition, String.class);
    }
}