package core.object.resolving.resolvers;

import core.Constants;
import core.object.resolving.ResolvingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IntegerFieldValueResolverTest {

    @Test
    void testResolvingFromString() {
        String rawValue = "0";
        IntegerFieldValueResolver resolver = new IntegerFieldValueResolver();
        Object value = resolver.resolve(rawValue);
        Assertions.assertEquals(0, value);
    }

    @Test
    void testFailResolvingEmptyString() {
        IntegerFieldValueResolver resolver = new IntegerFieldValueResolver();
        Assertions.assertThrows(ResolvingException.class, () ->
                resolver.resolve(Constants.EMPTY)
        );
    }

    @Test
    void testResolvingFromInteger() {
        int rawValue = 0;
        IntegerFieldValueResolver resolver = new IntegerFieldValueResolver();
        Object value = resolver.resolve(rawValue);
        Assertions.assertEquals(rawValue, value);
    }

    @Test
    void testResolvingNull() {
        IntegerFieldValueResolver resolver = new IntegerFieldValueResolver();
        Object value = resolver.resolve(null);
        Assertions.assertNull(value);
    }
}