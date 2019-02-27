package core.mapping.mappers.json;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public abstract class AbstractJsonCustomSerializer<T>  extends StdSerializer<T> implements JsonCustomizer<T>{

    AbstractJsonCustomSerializer(Class<T> t) {
        super(t);
    }

    @Override
    public void init(JsonCustomizerFactory hasSuitableClassObjects) {
        hasSuitableClassObjects.initSuitableObject( this);
    }

    protected void writeBooleanFieldIfTrue(JsonGenerator jsonGenerator, String fieldName, Boolean value) throws IOException {
        if(value){
            jsonGenerator.writeBooleanField(fieldName, true);
        }
    }
}
