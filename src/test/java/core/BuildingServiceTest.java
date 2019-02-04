package core;

import core.jpa.entity.EntityBlank;
import core.jpa.entity.building.BuildingService;
import javassist.CtClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.Assert;
import utils.ErrorUtils;
import utils.FactoryEntityBlank;

public class BuildingServiceTest {

    static BuildingService buildingService;

    @BeforeClass
    public static void initialize(){
        buildingService = new BuildingService();
    }

    @Test
    public void testBuild(){
        EntityBlank entityBlank = FactoryEntityBlank.create();

        ErrorUtils.assertNotError(()->{
            CtClass ctClass = buildingService.build(null, entityBlank);
            Assert.notNull(ctClass,"Not expected error with building EntityBlank");
        });
    }
}
