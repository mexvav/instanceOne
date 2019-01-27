package core.mapping;

public interface Mapper<F,T> {

    default void init(MappingService mappingService){
        mappingService.initMapper(this);
    }

    Class<F> getFromClass();

    Class<T> getToClass();

    T transform(F from);
}
