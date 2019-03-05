package core.mapping.mappers.json;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

@Component
public class JsonObjectMapperService {

    private ObjectMapper mapper;

    private SimpleModule module = new SimpleModule(
            JsonCustomizer.class.getName(),
            new Version(1, 0, 0, null, null, null));

    /**
     * Get {@link ObjectMapper}
     */
    public ObjectMapper getJsonMapper() {
        if (null == mapper) {
            mapper = new ObjectMapper();
        }
        return mapper;
    }

    /**
     * Add JsonCustomizer to ObjectMapper
     *
     * @param jsonCustomizer the registered {@link JsonCustomizer}
     */
    @SuppressWarnings("unchecked")
    public void add(JsonCustomizer jsonCustomizer) {
        if (jsonCustomizer instanceof AbstractJsonCustomSerializer) {
            module.addSerializer(jsonCustomizer.getRegisteredClass(),
                    (AbstractJsonCustomSerializer) jsonCustomizer);
        }
        if (jsonCustomizer instanceof AbstractJsonCustomDeserializer) {
            module.addDeserializer(jsonCustomizer.getRegisteredClass(),
                    (AbstractJsonCustomDeserializer) jsonCustomizer);
        }
        getJsonMapper().registerModule(module);
    }
}
