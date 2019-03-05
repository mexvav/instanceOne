package core.mapping;

import core.utils.register.RegisteredObject;

public interface Mapper<F, T> extends RegisteredObject {

    /**
     * Get class for mapped object
     */
    Class<F> getFromClass();

    /**
     * Get target class
     */
    Class<T> getToClass();

    /**
     * Transform object to target class
     */
    T transform(F from, Class<T> toClass);
}
