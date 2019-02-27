package core.mapping.mappers;

import core.mapping.Mapper;
import core.mapping.MappingService;

public abstract class AbstractMapper<F, T> implements Mapper<F, T> {

    private MappingService mappingService;

    @Override
    public void init(MappingService mappingService) {
        this.mappingService = mappingService;
        mappingService.initSuitableObject(this);
    }

    protected MappingService getMappingService() {
        return mappingService;
    }
}
