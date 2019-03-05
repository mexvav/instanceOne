package core.mapping.mappers.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import core.Constants;
import core.entity.field.EntityField;
import core.entity.field.EntityFieldFactory;
import core.interfaces.HasLength;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@SuppressWarnings("unused")
@Component
public class EntityFieldDeserializer extends AbstractJsonCustomDeserializer<EntityField> {

    private EntityFieldFactory entityFieldFactory;

    @Autowired
    private EntityFieldDeserializer(JsonObjectMapperService service, EntityFieldFactory entityFieldFactory) {
        super(EntityField.class, service);
        this.entityFieldFactory = entityFieldFactory;
    }

    @Override
    public EntityField deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);
        if (!node.has(Constants.HasCode.CODE) || !node.has((Constants.EntityField.TYPE))) {
            return null;
        }

        JsonNode type = node.get(Constants.EntityField.TYPE);
        JsonNode code = node.get(Constants.HasCode.CODE);
        EntityField entityField = entityFieldFactory.get(type.asText());
        entityField.setCode(code.asText());

        if (node.has(Constants.EntityField.REQUIRED)) {
            entityField.setRequired(node.get(Constants.EntityField.REQUIRED).asBoolean());
        }

        if (node.has(Constants.EntityField.UNIQUE)) {
            entityField.setUnique(node.get(Constants.EntityField.UNIQUE).asBoolean());
        }

        if (node.has(Constants.HasLength.LENGTH)) {
            ((HasLength) entityField).setLength(node.get(Constants.HasLength.LENGTH).asInt());
        }

        return entityField;
    }
}