package core;

import core.jpa.entity.EntityBlank;
import core.jpa.entity.building.BuilderService;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.FactoryEntityBlank;

public class BuilderServiceTest {

    static BuilderService builderService;

    @BeforeClass
    public static void initialize(){
        builderService = new BuilderService();
    }

    @Test
    public void testBuild(){
        EntityBlank entityBlank = FactoryEntityBlank.create();
        builderService.buildEntity(entityBlank);
    }
}
