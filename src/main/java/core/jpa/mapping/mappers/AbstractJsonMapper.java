package core.jpa.mapping.mappers;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import core.jpa.mapping.MappingException;
import core.jpa.mapping.mappers.json.AbstractJsonCustomDeserializer;
import core.jpa.mapping.mappers.json.AbstractJsonCustomSerializer;
import core.jpa.mapping.mappers.json.JsonCustomizer;
import core.utils.ReflectionUtils;

public abstract class AbstractJsonMapper<F, T> extends AbstractMapper<F, T> {
    private static ObjectMapper mapper;

    protected ObjectMapper getJsonMapper() {
        if (null == mapper) {
            initMapper();
        }
        return mapper;
    }
    @SuppressWarnings("unchecked")
    private void initMapper() {
        mapper = new ObjectMapper();

        SimpleModule module =
                new SimpleModule(JsonCustomizer.class.getName(), new Version(1, 0, 0, null, null, null));

        ReflectionUtils.actionWithSubTypes(AbstractJsonCustomSerializer.class.getPackage().getName(),
                AbstractJsonCustomSerializer.class, serializerClass -> {
                    try {
                        AbstractJsonCustomSerializer serializer = serializerClass.newInstance();
                        module.addSerializer(serializer.getSuitableClass(), serializer);
                    } catch (InstantiationException | IllegalAccessException e) {
                        throw new MappingException(e.getMessage());
                    }
                });

        ReflectionUtils.actionWithSubTypes(AbstractJsonCustomDeserializer.class.getPackage().getName(),
                AbstractJsonCustomDeserializer.class, deserializerClass -> {
                    try {
                        AbstractJsonCustomDeserializer deserializer = deserializerClass.newInstance();
                        module.addDeserializer(deserializer.getSuitableClass(), deserializer);
                    } catch (InstantiationException | IllegalAccessException e) {
                        throw new MappingException(e.getMessage());
                    }
                });
        mapper.registerModule(module);
    }
}
