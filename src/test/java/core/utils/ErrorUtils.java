package core.utils;

import org.junit.jupiter.api.Assertions;

import java.util.function.Supplier;

public class ErrorUtils {

    public static void assertNotError(Runnable errorOperation) {
        assertNotError(errorOperation);
    }

    public static void assertNotError(Runnable errorOperation, String message) {
        try {
            errorOperation.run();
        } catch (Exception e) {
            throw new AssertionError(String.format(Constants.Error.NOT_EXPECTED_ERROR, e.getMessage()));
        }
    }

    public static <R> R assertNotError(Supplier<R> errorOperation) {
        return assertNotError(errorOperation, Constants.Error.NOT_EXPECTED_ERROR);
    }

    public static <R> R assertNotError(Supplier<R> errorOperation, String message) {
        try {
            return errorOperation.get();
        } catch (Exception e) {
            throw new AssertionError(String.format(message, e.getMessage()));
        }
    }

    public static void assertError(Runnable errorOperation, String expectedError) {
        assertError(errorOperation, expectedError, Constants.Error.NOT_EXPECTED_ERROR);
    }

    public static void assertError(Runnable errorOperation, String expectedError, String message) {
        try {
            errorOperation.run();
        } catch (Exception e) {
            Assertions.assertTrue(e.getMessage().contains(expectedError), message);
        }
    }
}
