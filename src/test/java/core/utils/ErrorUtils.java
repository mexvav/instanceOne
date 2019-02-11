package core.utils;

import org.junit.Assert;

public class ErrorUtils {

    public static void assertNotError(Runnable errorOperation) {
        try {
            errorOperation.run();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
}
