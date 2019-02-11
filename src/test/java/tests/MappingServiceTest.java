package tests;

import core.jpa.entity.EntityBlank;
import core.jpa.mapping.MappingService;
import core.utils.FactoryEntityBlank;
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
        EntityBlank entityBlank = FactoryEntityBlank.create();
        String json = mappingService.mapping(entityBlank, String.class);
        EntityBlank mappedEntityBlank = mappingService.mapping(json, EntityBlank.class);

        Assertions.assertEquals(entityBlank, mappedEntityBlank);
    }
}
