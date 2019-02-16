package core.jpa.mapping.mappers;

import core.jpa.mapping.MappingService;

public interface Mapper<F, T> {

    Class<F> getFromClass();

    Class<T> getToClass();

    T transform(F from, Class<T> toClass);

    void init(MappingService mappingService);
}
