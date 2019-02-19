package core.jpa.mapping.mappers.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import core.jpa.Constants;
import core.jpa.entity.field.EntityField;
import core.jpa.interfaces.HasLength;

import java.io.IOException;
import java.util.Arrays;

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
        jsonGenerator.writeStringField(Constants.EntityField.TYPE, entityField.getType());
        writeBooleanFieldIfTrue(jsonGenerator, Constants.EntityField.REQUIRED, entityField.isRequired());
        writeBooleanFieldIfTrue(jsonGenerator, Constants.EntityField.UNIQUE,  entityField.isUnique());

        setLength(jsonGenerator, entityField);

        jsonGenerator.writeEndObject();
    }

    /**
     * Set length for json
     *
     * @param jsonGenerator jsonGenerator
     * @param entityField   field for column
     */
    private void setLength(JsonGenerator jsonGenerator, EntityField entityField) throws IOException{
        if (!Arrays.asList(entityField.getClass().getInterfaces()).contains(HasLength.class)) {
            return;
        }
        int length = ((HasLength) entityField).getLength();
        if (length == Constants.HasLength.DEFAUIT) {
            return;
        }
        jsonGenerator.writeNumberField(Constants.HasLength.LENGTH,length);
    }
}