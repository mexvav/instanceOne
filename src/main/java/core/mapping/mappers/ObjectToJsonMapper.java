package core.mapping.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.mapping.MappingException;
import core.mapping.MappingService;
import core.mapping.mappers.json.JsonObjectMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@SuppressWarnings("unused")
@Component
public class ObjectToJsonMapper extends AbstractMapper<Object, String> {

    private ObjectMapper objectMapper;

    @Autowired
    ObjectToJsonMapper(MappingService mappingService, JsonObjectMapperService jsonObjectMapperService){
        super(mappingService, Object.class, String.class);
        this.objectMapper = jsonObjectMapperService.getJsonMapper();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String transform(Object from, Class<String> toClass) {
        try {
            return objectMapper.writeValueAsString(from);
        } catch (JsonProcessingException e) {
            throw new MappingException(e);
        }
    }
}