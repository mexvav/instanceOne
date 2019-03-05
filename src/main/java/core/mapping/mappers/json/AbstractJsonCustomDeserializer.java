package core.mapping.mappers.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import core.mapping.MappingException;

import java.io.IOException;

public abstract class AbstractJsonCustomDeserializer<T> extends StdDeserializer<T> implements JsonCustomizer<T> {

    private Class<T> registeredClass;

    AbstractJsonCustomDeserializer(Class<T> registeredClass, JsonObjectMapperService jsonObjectMapperService) {
        super(registeredClass);
        this.registeredClass = registeredClass;
        jsonObjectMapperService.add(this);
    }

    @Override
    public Class<T> getRegisteredClass() {
        return registeredClass;
    }

    /**
     * Get value, use standard Deserializer
     */
    @SuppressWarnings("unchecked")
    protected <R> R getValue(DeserializationContext deserializationContext, JsonNode node, Class<R> type) {
        try {
            JavaType jacksonType = deserializationContext.getTypeFactory().constructType(type);
            JsonDeserializer deserializer = deserializationContext.findRootValueDeserializer(jacksonType);
            JsonParser nodeParser = node.traverse(deserializationContext.getParser().getCodec());
            nodeParser.nextToken();
            return (R) deserializer.deserialize(nodeParser, deserializationContext);
        } catch (IOException e) {
            throw new MappingException(e);
        }
    }
}
