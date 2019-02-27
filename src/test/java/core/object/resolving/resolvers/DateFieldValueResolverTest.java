package core.object.resolving.resolvers;

import core.Constants;
import core.object.resolving.ResolvingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateFieldValueResolverTest {

    @Test
    void testResolvingFromString() {
        String rawValue = "01.01.1970";

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date(0));
        int expectedYear = calendar.get(Calendar.YEAR);

        DateFieldValueResolver resolver = new DateFieldValueResolver();
        Date actualDate = resolver.resolve(rawValue);
        calendar.setTime(actualDate);
        int actualYear = calendar.get(Calendar.YEAR);

        Assertions.assertEquals(expectedYear, actualYear);
    }

    @Test
    void testResolvingFromDate() {
        Date rawValue = new Date(0);

        DateFieldValueResolver resolver = new DateFieldValueResolver();
        Date actualDate = resolver.resolve(rawValue);

        Assertions.assertEquals(rawValue, actualDate);
    }

    @Test
    void testFailResolvingFromInteger() {
        DateFieldValueResolver resolver = new DateFieldValueResolver();
        Assertions.assertThrows(ResolvingException.class, () ->
                resolver.resolve(0)
        );
    }

    @Test
    void testFailResolvingEmptyString() {
        DateFieldValueResolver resolver = new DateFieldValueResolver();
        Assertions.assertThrows(ResolvingException.class, () ->
                resolver.resolve(Constants.EMPTY)
        );
    }
}
