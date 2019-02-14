package core.jpa.mapping.mappers;

import com.google.common.collect.Maps;
import core.jpa.Constants;
import core.jpa.entity.fields.types.EntityFieldType;
import core.jpa.mapping.MappingException;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Set;

public class MapToAttributeTypeMapper extends AbstractMapper<Map, EntityFieldType> {

    private Map<String, Class<? extends EntityFieldType>> attributeTypes;

    @Override
    public Class<Map> getFromClass() {
        return Map.class;
    }

    @Override
    public Class<EntityFieldType> getToClass() {
        return EntityFieldType.class;
    }

    @Override
    public EntityFieldType transform(Map from) {
        String code = (String) from.get(Constants.HasCode.CODE);
        Class<? extends EntityFieldType> attributeTypeClass = getAttributeTypesClass(code);
        try {
            from.put("instance", attributeTypeClass.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new MappingException(e.getMessage());
        }
        return getMappingService().mapping(from, getAttributeTypesClass(code));
    }

    private Map<String, Class<? extends EntityFieldType>> getAttributeTypes() {
        if (null == attributeTypes) {
            attributeTypes = Maps.newHashMap();
            Reflections reflections = new Reflections(EntityFieldType.class.getPackage().getName());
            Set<Class<? extends EntityFieldType>> attributeTypeClasses =
                    reflections.getSubTypesOf(EntityFieldType.class);
            for (Class<? extends EntityFieldType> typeClass : attributeTypeClasses) {
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

    private Class<? extends EntityFieldType> getAttributeTypesClass(String code) {
        return getAttributeTypes().get(code);
    }
}
