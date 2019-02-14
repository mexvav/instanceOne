package core.utils;


import java.util.function.Supplier;

public class ErrorUtils {

    public static void assertNotError(Runnable errorOperation) {
        assertNotError(errorOperation);
    }

    public static void assertNotError(Runnable errorOperation, String message) {
        try {
            errorOperation.run();
        } catch (Exception e) {
            throw new AssertionError(String.format(Constans.Error.NOT_EXPECTED_ERROR, e.getMessage()));
        }
    }

    public static <R> R assertNotError(Supplier<R> errorOperation) {
        return assertNotError(errorOperation, Constans.Error.NOT_EXPECTED_ERROR);
    }

    public static <R> R assertNotError(Supplier<R> errorOperation, String message) {
        try {
            return errorOperation.get();
        } catch (Exception e) {
            throw new AssertionError(String.format(message, e.getMessage()));
        }
    }


}
