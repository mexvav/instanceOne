package core.mapping.mappers.json;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public abstract class AbstractJsonCustomSerializer<T>  extends StdSerializer<T> implements JsonCustomizer<T>{

    private Class<T> registeredClass;

    AbstractJsonCustomSerializer(Class<T> registeredClass, JsonObjectMapperService jsonObjectMapperService) {
        super(registeredClass);
        this.registeredClass = registeredClass;
        jsonObjectMapperService.add(this);
    }

    @Override
    public Class<T> getRegisteredClass() {
        return registeredClass;
    }

    protected void writeBooleanFieldIfTrue(JsonGenerator jsonGenerator, String fieldName, Boolean value) throws IOException {
        if(value){
            jsonGenerator.writeBooleanField(fieldName, true);
        }
    }
}
