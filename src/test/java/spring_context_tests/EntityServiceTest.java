package spring_context_tests;

import core.jpa.entity.EntityBlank;
import core.jpa.entity.EntityService;
import core.utils.FactoryEntityBlank;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class EntityServiceTest extends SpringContextAbstractTest{

    @Autowired
    private EntityService entityService;

    @Test
    void testEntityService(){
        EntityBlank entityBlank = FactoryEntityBlank.create();
        entityService.createEntity(entityBlank);

    }
}
