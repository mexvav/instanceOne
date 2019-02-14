package core.jpa.mapping.mappers;

import core.jpa.mapping.MappingService;

public abstract class AbstractMapper<F, T> implements Mapper<F, T> {

    private MappingService mappingService;

    @Override
    public void init(MappingService mappingService) {
        this.mappingService = mappingService;
    }

    protected MappingService getMappingService() {
        return mappingService;
    }
}
