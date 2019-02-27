package core.mapping.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.mapping.mappers.json.JsonCustomizerFactory;

public abstract class AbstractJsonMapper<F, T>  extends AbstractMapper<F, T> {

    private static JsonCustomizerFactory jsonCustomizerFactory;

    protected ObjectMapper getJsonMapper() {
        if (null == jsonCustomizerFactory) {
            jsonCustomizerFactory = new JsonCustomizerFactory();
        }
        return jsonCustomizerFactory.getJsonMapper();
    }

}
