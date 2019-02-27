package core.object.resolving.resolvers;

import core.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StringFieldValueResolverTest {

    @Test
    void testResolvingFromString() {
        String rawValue = "value";
        StringFieldValueResolver resolver = new StringFieldValueResolver();
        Object value = resolver.resolve(rawValue);
        Assertions.assertEquals(rawValue, value);
    }

    @Test
    void testResolvingEmptyString() {
        StringFieldValueResolver resolver = new StringFieldValueResolver();
        Object value = resolver.resolve(Constants.EMPTY);
        Assertions.assertEquals(Constants.EMPTY, value);
    }

    @Test
    void testResolvingFromInteger() {
        int rawValue = 0;
        StringFieldValueResolver resolver = new StringFieldValueResolver();
        Object value = resolver.resolve(rawValue);
        Assertions.assertEquals("0", value);
    }

    @Test
    void testResolvingNull() {
        StringFieldValueResolver resolver = new StringFieldValueResolver();
        Object value = resolver.resolve(null);
        Assertions.assertNull(value);
    }
}