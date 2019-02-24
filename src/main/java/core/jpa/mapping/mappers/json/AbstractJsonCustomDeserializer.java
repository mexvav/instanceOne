package core.jpa.mapping.mappers.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import core.jpa.mapping.MappingException;
import core.jpa.mapping.mappers.AbstractJsonMapper;
import core.utils.suitable.HasSuitableObjectsByClass;
import core.utils.suitable.SuitableObjectByClass;

import java.io.IOException;

public abstract class AbstractJsonCustomDeserializer<T> extends StdDeserializer<T> implements JsonCustomizer<T> {

    AbstractJsonCustomDeserializer(Class<T> t) {
        super(t);
    }

    @Override
    public void init(JsonCustomizerFactory hasSuitableClassObjects) {
        hasSuitableClassObjects.initSuitableObject( this);
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