package core.object.searching.operation;

import core.object.searching.OperationFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test_core.factories.EntityFieldFactory;
import test_core.utils.SpringContextUtils;

class GreaterOperationTest extends AbstractOperationTest {

    @Autowired
    GreaterOperationTest(SpringContextUtils utils) {
        super(utils);
    }

    @Test
    void testSearchObjectWithIntegerField() {
        assertObjectFound(EntityFieldFactory.integerEntityField(),
                20, OperationFactory.greater(10));
    }

    @Test
    void testNotFoundObjectWithIntegerField() {
        assertObjectNotFound(EntityFieldFactory.integerEntityField(),
                10, OperationFactory.greater(20));
    }
}