package tests;

import core.factories.EntityClassFactory;
import core.jpa.entity.EntityClass;
import core.jpa.mapping.MappingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class MappingServiceTest {

    private static MappingService mappingService;

    @BeforeAll
    static void initialize() {
        mappingService = new MappingService();
    }

    @Test
    void testMappingEntityBlank() {
        EntityClass entityClass = EntityClassFactory.create();
        String json = mappingService.mapping(entityClass, String.class);
        EntityClass mappedEntityClass = mappingService.mapping(json, EntityClass.class);

        Assertions.assertEquals(entityClass, mappedEntityClass);
    }

    @Test
    void test() {
        EntityClass entityClass = EntityClassFactory.create();
        String json = mappingService.mapping(entityClass, String.class);
        EntityClass mappedEntityClass = mappingService.mapping(json, EntityClass.class);

        Assertions.assertEquals(entityClass, mappedEntityClass);
    }
}
