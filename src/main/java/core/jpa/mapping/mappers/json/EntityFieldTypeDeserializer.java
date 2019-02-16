package core.jpa.mapping.mappers.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import core.jpa.Constants;
import core.jpa.entity.fields.types.EntityFieldType;
import core.jpa.entity.fields.types.EntityFieldTypeFactoty;
import core.jpa.mapping.MappingException;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Arrays;

@SuppressWarnings("unused")
public class EntityFieldTypeDeserializer extends AbstractJsonCustomDeserializer<EntityFieldType> {

    public EntityFieldTypeDeserializer() {
        this(null);
    }

    private EntityFieldTypeDeserializer(Class<EntityFieldType> t) {
        super(t);
    }

    @Override
    public Class<EntityFieldType> getSuitableClass() {
        return EntityFieldType.class;
    }

    @Override
    public EntityFieldType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);
        String code;
        if (node.isObject()) {
            if (!node.has(Constants.HasCode.CODE)) {
                return null;
            }
            code = node.get(Constants.HasCode.CODE).asText();
        } else {
            code = node.asText();
        }

        EntityFieldType entityFieldType = EntityFieldTypeFactoty.createByCode(code);
        Arrays.asList(entityFieldType.getClass().getDeclaredFields()).forEach(field -> {
            if (!Modifier.isStatic(field.getModifiers()) && node.has(field.getName())) {
                field.setAccessible(true);
                try {
                    JavaType jacksonType = deserializationContext.getTypeFactory().constructType(field.getType());
                    JsonDeserializer deserializer = deserializationContext.findRootValueDeserializer(jacksonType);
                    JsonParser nodeParser = node.get(field.getName()).traverse(deserializationContext.getParser().getCodec());
                    nodeParser.nextToken();

                    Object value = deserializer.deserialize(nodeParser, deserializationContext);
                    field.set(entityFieldType, value);
                } catch (IllegalAccessException | IOException e) {
                    throw new MappingException(e.getMessage());
                }
            }
        });
        return entityFieldType;
    }
}