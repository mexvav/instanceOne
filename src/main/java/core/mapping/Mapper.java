package core.mapping;

import core.utils.suitable.SuitableObjectByClass;

public interface Mapper<F, T> extends SuitableObjectByClass<F, MappingService> {

    Class<T> getToClass();

    T transform(F from, Class<T> toClass);
}
