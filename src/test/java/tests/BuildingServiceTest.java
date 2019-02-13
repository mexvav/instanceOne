package tests;

import core.jpa.entity.EntityBlank;
import core.jpa.entity.building.BuildingService;

import core.utils.ErrorUtils;
import core.utils.FactoryEntityBlank;
import net.bytebuddy.dynamic.DynamicType.Builder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BuildingServiceTest {

    private static BuildingService buildingService;

    @BeforeAll
    static void initialize() {
        buildingService = new BuildingService();
    }

    @Test
    void testBuild() {
        EntityBlank entityBlank = FactoryEntityBlank.create();

        ErrorUtils.assertNotError(() -> {
            Builder ctClass = buildingService.build(null, entityBlank);
            Assertions.assertNotNull(ctClass, "Not expected error with building EntityBlank");
        });
    }
}
