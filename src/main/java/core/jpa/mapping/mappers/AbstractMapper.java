package core.jpa.mapping.mappers;

import core.jpa.mapping.Mapper;
import core.jpa.mapping.MappingService;

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
