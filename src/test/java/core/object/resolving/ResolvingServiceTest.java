package core.object.resolving;

import core.entity.field.EntityField;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test_core.AbstractSpringContextTest;
import test_core.factories.EntityFieldFactory;

class ResolvingServiceTest extends AbstractSpringContextTest {

    private ResolvingService resolvingService;

    @Autowired
    ResolvingServiceTest(ResolvingService resolvingService) {
        this.resolvingService = resolvingService;
    }

    @Test
    @SuppressWarnings("unchecked")
    void testResolveStringFieldFromIntegerValue() {
        EntityField entityField = EntityFieldFactory.stringEntityField();
        int rawValue = 0;
        Object value = resolvingService.resolve(entityField, rawValue);
        Assertions.assertEquals("0", value);
    }

    @Test
    @SuppressWarnings("unchecked")
    void testResolveStringFieldFromNull() {
        EntityField entityField = EntityFieldFactory.stringEntityField();
        Object value = resolvingService.resolve(entityField, null);
        Assertions.assertNull(value);
    }
}
