package core;

import core.jpa.entity.EntityBlank;
import core.jpa.mapping.MappingService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.FactoryEntityBlank;

public class MappingServiceTest {

    static MappingService mappingService;

    @BeforeClass
    public static void initialize(){
        mappingService = new MappingService();
    }

    @Test
    public void testMappingEntityBlank(){
        EntityBlank entityBlank = FactoryEntityBlank.create();
        String json = mappingService.mapping(entityBlank, String.class);
        EntityBlank mappedEntityBlank = mappingService.mapping(json, EntityBlank.class);

        Assert.assertEquals(entityBlank, mappedEntityBlank);
    }
}
