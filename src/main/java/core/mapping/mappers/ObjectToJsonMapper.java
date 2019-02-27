package core.mapping.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import core.mapping.MappingException;

public class ObjectToJsonMapper extends AbstractJsonMapper<Object, String> {

    @Override
    public Class<Object> getSuitableClass() {
        return Object.class;
    }

    @Override
    public Class<String> getToClass() {
        return String.class;
    }

    @Override
    public String transform(Object from, Class<String> toClass) {
        try {
            return getJsonMapper().writeValueAsString(from);
        } catch (JsonProcessingException e) {
            throw new MappingException(e);
        }
    }
}