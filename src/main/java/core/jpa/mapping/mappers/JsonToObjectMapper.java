package core.jpa.mapping.mappers;

import core.jpa.mapping.MappingException;

import java.io.IOException;

public class JsonToObjectMapper extends AbstractJsonMapper<String, Object> {

    @Override
    public Class<String> getFromClass() {
        return String.class;
    }

    @Override
    public Class<Object> getToClass() {
        return Object.class;
    }

    @Override
    public Object transform(String from, Class<Object> to) {
        try {
            return getJsonMapper().readValue(from, to);
        } catch (IOException e) {
            throw new MappingException(e.getMessage());
        }
    }
}