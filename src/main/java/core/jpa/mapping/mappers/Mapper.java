package core.jpa.mapping.mappers;

import core.jpa.mapping.MappingService;

public interface Mapper<F,T> {

    Class<F> getFromClass();

    Class<T> getToClass();

    T transform(F from);

    void init(MappingService mappingService);
}
