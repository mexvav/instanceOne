package core.object.resolving.resolvers;

import core.Constants;
import core.object.resolving.ResolvingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test_core.AbstractSpringContextTest;

class IntegerFieldValueResolverTest extends AbstractSpringContextTest {

    private IntegerFieldValueResolver resolver;

    @Autowired
    IntegerFieldValueResolverTest(IntegerFieldValueResolver resolver) {
        this.resolver = resolver;
    }

    @Test
    void testResolvingFromString() {
        String rawValue = "0";
        Object value = resolver.resolve(rawValue);
        Assertions.assertEquals(0, value);
    }

    @Test
    void testFailResolvingEmptyString() {
        Assertions.assertThrows(ResolvingException.class, () ->
                resolver.resolve(Constants.EMPTY)
        );
    }

    @Test
    void testResolvingFromInteger() {
        int rawValue = 0;
        Object value = resolver.resolve(rawValue);
        Assertions.assertEquals(rawValue, value);
    }

    @Test
    void testResolvingNull() {
        Object value = resolver.resolve(null);
        Assertions.assertNull(value);
    }
}