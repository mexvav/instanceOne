package core.mapping.mappers.json;

import core.utils.suitable.SuitableObjectByClass;

public interface JsonCustomizer<T> extends SuitableObjectByClass<T, JsonCustomizerFactory> {

    /**
     * Get suitable class for this customizer
     */
    Class<T> getSuitableClass();
}
