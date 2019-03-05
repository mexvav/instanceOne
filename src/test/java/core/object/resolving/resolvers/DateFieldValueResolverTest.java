package core.object.resolving.resolvers;

import core.Constants;
import core.object.resolving.ResolvingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test_core.AbstractSpringContextTest;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

class DateFieldValueResolverTest extends AbstractSpringContextTest {

    private DateFieldValueResolver resolver;

    @Autowired
    DateFieldValueResolverTest(DateFieldValueResolver resolver) {
        this.resolver = resolver;
    }

    @Test
    void testResolvingFromString() {
        String rawValue = "01.01.1970";

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date(0));
        int expectedYear = calendar.get(Calendar.YEAR);

        Date actualDate = resolver.resolve(rawValue);
        calendar.setTime(actualDate);
        int actualYear = calendar.get(Calendar.YEAR);

        Assertions.assertEquals(expectedYear, actualYear);
    }

    @Test
    void testResolvingFromDate() {
        Date rawValue = new Date(0);
        Date actualDate = resolver.resolve(rawValue);
        Assertions.assertEquals(rawValue, actualDate);
    }

    @Test
    void testFailResolvingFromInteger() {
        Assertions.assertThrows(ResolvingException.class, () ->
                resolver.resolve(0)
        );
    }

    @Test
    void testFailResolvingEmptyString() {
        Assertions.assertThrows(ResolvingException.class, () ->
                resolver.resolve(Constants.EMPTY)
        );
    }
}
