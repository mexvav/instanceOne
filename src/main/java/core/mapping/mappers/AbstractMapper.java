package core.mapping.mappers;

import core.mapping.Mapper;
import core.mapping.MappingService;

public abstract class AbstractMapper<F, T> implements Mapper<F, T> {

    private MappingService mappingService;
    private Class<F> fromClass;
    private Class<T> toClass;

    protected AbstractMapper(MappingService mappingService, Class<F> fromClass, Class<T> toClass) {
        this.mappingService = mappingService;
        this.fromClass = fromClass;
        this.toClass = toClass;

        mappingService.register(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<F> getFromClass() {
        return fromClass;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<T> getToClass() {
        return toClass;
    }

    /**
     * Get {@link MappingService}
     */
    protected MappingService getMappingService() {
        return mappingService;
    }
}
