package core.object.resolving.resolvers;

import core.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test_core.AbstractSpringContextTest;

class StringFieldValueResolverTest extends AbstractSpringContextTest {

    private StringFieldValueResolver resolver;

    @Autowired
    StringFieldValueResolverTest(StringFieldValueResolver resolver) {
        this.resolver = resolver;
    }

    @Test
    void testResolvingFromString() {
        String rawValue = "value";
        Object value = resolver.resolve(rawValue);
        Assertions.assertEquals(rawValue, value);
    }

    @Test
    void testResolvingEmptyString() {
        Object value = resolver.resolve(Constants.EMPTY);
        Assertions.assertEquals(Constants.EMPTY, value);
    }

    @Test
    void testResolvingFromInteger() {
        int rawValue = 0;
        Object value = resolver.resolve(rawValue);
        Assertions.assertEquals("0", value);
    }

    @Test
    void testResolvingNull() {
        Object value = resolver.resolve(null);
        Assertions.assertNull(value);
    }
}