package core.object.searching.operation;

import core.object.searching.OperationFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test_core.factories.EntityFieldFactory;
import test_core.utils.SpringContextUtils;

class LessOperationTest extends AbstractOperationTest {

    @Autowired
    LessOperationTest(SpringContextUtils utils) {
        super(utils);
    }

    @Test
    void testSearchObjectWithIntegerField() {
        assertObjectFound(EntityFieldFactory.integerEntityField(),
                10, OperationFactory.less(20));
    }

    @Test
    void testNotFoundObjectWithIntegerField() {
        assertObjectNotFound(EntityFieldFactory.integerEntityField(),
                20, OperationFactory.less(10));
    }
}