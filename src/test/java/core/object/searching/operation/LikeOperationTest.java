package core.object.searching.operation;

import core.object.searching.OperationFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test_core.factories.EntityFieldFactory;
import test_core.utils.SpringContextUtils;

class LikeOperationTest extends AbstractOperationTest {

    @Autowired
    LikeOperationTest(SpringContextUtils utils) {
        super(utils);
    }

    @Test
    void testSearchObjectWithIntegerField() {
        assertObjectFound(EntityFieldFactory.stringEntityField(),
                "testing string", OperationFactory.like("string"));
    }

    @Test
    void testNotFoundObjectWithIntegerField() {
        assertObjectNotFound(EntityFieldFactory.stringEntityField(),
                "testing string", OperationFactory.like("something"));
    }
}