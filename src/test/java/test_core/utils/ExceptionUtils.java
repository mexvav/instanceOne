package test_core.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

public class ExceptionUtils {

    private static final String EXCEPTION_NOT_THROW =
            "Expected exception by class \"%s\" and message \"%s\" not throw";

    private static final String EXCEPTION_NOT_EXPECTED =
            "Expected exception with message \"%s\", but actual \"%s\"";

    /**
     * Assert exception
     *
     * @param expectedType    expected class of exception
     * @param executable      actions, throwing exception
     * @param expectedMessage expected exception message
     */
    public static void assertThrows(Class<? extends Exception> expectedType, Executable executable, String expectedMessage) {
        String message = String.format(EXCEPTION_NOT_THROW, expectedType.getName(), expectedMessage);
        Exception thrown = Assertions.assertThrows(expectedType, executable, message);

        message = String.format(EXCEPTION_NOT_EXPECTED, expectedMessage, thrown.getMessage());
        Assertions.assertTrue(thrown.getMessage().contains(expectedMessage), message);
    }

}
