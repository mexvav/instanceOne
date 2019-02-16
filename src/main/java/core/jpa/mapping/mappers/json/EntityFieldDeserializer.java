package core.jpa.mapping.mappers.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import core.jpa.Constants;
import core.jpa.entity.fields.EntityField;
import core.jpa.entity.fields.types.EntityFieldType;
import core.jpa.entity.fields.types.EntityFieldTypeFactoty;

import java.io.IOException;

@SuppressWarnings("unused")
public class EntityFieldDeserializer extends AbstractJsonCustomDeserializer<EntityField> {

    public EntityFieldDeserializer() {
        this(null);
    }

    private EntityFieldDeserializer(Class<EntityField> t) {
        super(t);
    }

    @Override
    public Class<EntityField> getSuitableClass() {
        return EntityField.class;
    }

    @Override
    public EntityField deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);
        if (!node.has(Constants.HasCode.CODE) || !node.has((Constants.EntityField.TYPE))) {
            return null;
        }

        JsonNode code = node.get(Constants.HasCode.CODE);
        EntityField entityField = new EntityField(code.asText());

        if (node.has(Constants.EntityField.REQUIRED)) {
            entityField.setRequired(node.get(Constants.EntityField.REQUIRED).asBoolean());
        }

        if (node.has(Constants.EntityField.UNIQUE)) {
            entityField.setUnique(node.get(Constants.EntityField.UNIQUE).asBoolean());
        }

        JsonNode type = node.get(Constants.EntityField.TYPE);
        EntityFieldType entityFieldType = getValue(deserializationContext, type, EntityFieldType.class);
        entityField.setType(entityFieldType);

        return entityField;
    }
}