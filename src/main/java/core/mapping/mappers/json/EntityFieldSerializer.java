package core.mapping.mappers.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import core.Constants;
import core.entity.field.EntityField;
import core.interfaces.HasLength;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

@SuppressWarnings("unused")
@Component
public class EntityFieldSerializer extends AbstractJsonCustomSerializer<EntityField> {

    @Autowired
    private EntityFieldSerializer(JsonObjectMapperService service) {
        super(EntityField.class, service);
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