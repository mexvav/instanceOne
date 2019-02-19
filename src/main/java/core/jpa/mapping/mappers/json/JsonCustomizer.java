package core.jpa.mapping.mappers.json;

import core.jpa.mapping.mappers.AbstractJsonMapper;
import core.jpa.mapping.mappers.JsonToObjectMapper;
import core.utils.suitable.SuitableObjectByClass;

public interface JsonCustomizer<T> extends SuitableObjectByClass<T, JsonCustomizerFactory> {

    /**
     * Get suitable class for this customizer
     */
    Class<T> getSuitableClass();
}
