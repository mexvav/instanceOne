package core.mapping.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.mapping.MappingException;
import core.mapping.MappingService;
import core.mapping.mappers.json.JsonObjectMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@SuppressWarnings("unused")
@Component
public class JsonToObjectMapper extends AbstractMapper<String, Object> {

    private ObjectMapper objectMapper;

    @Autowired
    JsonToObjectMapper(MappingService mappingService, JsonObjectMapperService jsonObjectMapperService) {
        super(mappingService, String.class, Object.class);
        this.objectMapper = jsonObjectMapperService.getJsonMapper();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object transform(String from, Class<Object> to) {
        try {
            return objectMapper.readValue(from, to);
        } catch (IOException e) {
            throw new MappingException(e);
        }
    }
}