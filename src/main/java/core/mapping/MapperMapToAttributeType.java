package core.mapping;

import com.google.common.collect.Maps;
import core.jpa.attribute.type.AttributeSimpleType;
import core.jpa.attribute.type.AttributeType;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class MapperMapToAttributeType extends MapperAbstract<Map, AttributeType> {

    private Map<String, Class<? extends AttributeType>> attributeTypes;

    @Override
    public Class<Map> getFromClass(){
        return Map.class;
    }

    @Override
    public Class<AttributeType> getToClass(){
        return AttributeType.class;
    }

    @Override
    public AttributeType transform(Map from) {
        String code = (String)from.get("code");
        Class<? extends AttributeType> attributeTypeClass = getAttributeTypesClass(code);

        try {
            from.put("instance", attributeTypeClass.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new MappingService.MappingException(e.getMessage());
        }

        return getMappingService().mapping(from, getAttributeTypesClass(code));
    }

    private Map<String, Class<? extends AttributeType>> getAttributeTypes(){
        if(null == attributeTypes){
            attributeTypes = Maps.newHashMap();
            Reflections reflections = new Reflections(AttributeType.class.getPackage().getName());
            Set<Class<? extends AttributeType>> attributeTypeClasses = reflections.getSubTypesOf(AttributeType.class);
            for(Class<? extends AttributeType> typeClass : attributeTypeClasses){
                try {
                    attributeTypes.put(typeClass.newInstance().getCode(), typeClass);
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new MappingService.MappingException(e.getMessage());
                }
            }
        }
        return attributeTypes;
    }

    private Class<? extends AttributeType> getAttributeTypesClass(String code){
        return getAttributeTypes().get(code);
    }
}