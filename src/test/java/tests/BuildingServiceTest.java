package tests;

import core.jpa.entity.EntityBlank;
import core.jpa.entity.building.BuildingService;
import javassist.CtClass;
import core.utils.ErrorUtils;
import core.utils.FactoryEntityBlank;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BuildingServiceTest {

    static BuildingService buildingService;

    @BeforeAll
    public static void initialize(){
        buildingService = new BuildingService();
    }

    @Test
    public void testBuild(){
        EntityBlank entityBlank = FactoryEntityBlank.create();

        ErrorUtils.assertNotError(()->{
            CtClass ctClass = buildingService.build(null, entityBlank);
            Assertions.assertNotNull(ctClass,"Not expected error with building EntityBlank");
        });
    }
}
