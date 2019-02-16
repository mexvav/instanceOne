package core.jpa.mapping.mappers.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import core.jpa.Constants;
import core.jpa.entity.fields.EntityField;
import core.jpa.entity.fields.types.EntityFieldType;

import java.io.IOException;

@SuppressWarnings("unused")
public class EntityFieldSerializer extends AbstractJsonCustomSerializer<EntityField> {

    public EntityFieldSerializer() {
        this(null);
    }

    private EntityFieldSerializer(Class<EntityField> t) {
        super(t);
    }

    @Override
    public Class<EntityField> getSuitableClass() {
        return EntityField.class;
    }

    @Override
    public void serialize(EntityField entityField, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeStringField(Constants.HasCode.CODE, entityField.getCode());
        writeBooleanFieldIfTrue(jsonGenerator, Constants.EntityField.REQUIRED, entityField.isRequired());
        writeBooleanFieldIfTrue(jsonGenerator, Constants.EntityField.UNIQUE,  entityField.isUnique());

        EntityFieldType entityFieldType = entityField.getType();
        if (entityFieldType.getClass().getDeclaredFields().length > 1) {
            jsonGenerator.writeObjectField(Constants.EntityField.TYPE, entityFieldType);
        } else {
            jsonGenerator.writeStringField(Constants.EntityField.TYPE, entityFieldType.getCode());
        }

        jsonGenerator.writeEndObject();
    }
}