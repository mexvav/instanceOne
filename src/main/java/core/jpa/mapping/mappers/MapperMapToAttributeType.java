package core.jpa.mapping.mappers;

import com.google.common.collect.Maps;
import core.jpa.Constants;
import core.jpa.entity.attribute.type.AttributeType;
import core.jpa.mapping.MappingException;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Set;

public class MapperMapToAttributeType extends MapperAbstract<Map, AttributeType> {

    private Map<String, Class<? extends AttributeType>> attributeTypes;

    @Override
    public Class<Map> getFromClass() {
        return Map.class;
    }

    @Override
    public Class<AttributeType> getToClass() {
        return AttributeType.class;
    }

    @Override
    public AttributeType transform(Map from) {
        String code = (String) from.get(Constants.HasCode.CODE);
        Class<? extends AttributeType> attributeTypeClass = getAttributeTypesClass(code);
        try {
            from.put("instance", attributeTypeClass.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new MappingException(e.getMessage());
        }
        return getMappingService().mapping(from, getAttributeTypesClass(code));
    }

    private Map<String, Class<? extends AttributeType>> getAttributeTypes() {
        if (null == attributeTypes) {
            attributeTypes = Maps.newHashMap();
            Reflections reflections = new Reflections(AttributeType.class.getPackage().getName());
            Set<Class<? extends AttributeType>> attributeTypeClasses = reflections.getSubTypesOf(AttributeType.class);
            for (Class<? extends AttributeType> typeClass : attributeTypeClasses) {
                if (!typeClass.isInterface() && !Modifier.isAbstract(typeClass.getModifiers())) {
                    try {
                        attributeTypes.put(typeClass.newInstance().getCode(), typeClass);
                    } catch (InstantiationException | IllegalAccessException e) {
                        throw new MappingException(e.getMessage());
                    }
                }
            }
        }
        return attributeTypes;
    }

    private Class<? extends AttributeType> getAttributeTypesClass(String code) {
        return getAttributeTypes().get(code);
    }
}