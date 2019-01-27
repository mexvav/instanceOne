package core.mapping;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class MapperAbstract<F,T> implements Mapper<F,T> {

    private MappingService mappingService;

    @Autowired
    void AbstractMapper(MappingService mappingService){
        this.mappingService = mappingService;
        init(mappingService);
    }

    protected MappingService getMappingService(){
        return mappingService;
    }
}
