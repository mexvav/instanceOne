package utils;

import org.junit.Assert;

public class ErrorUtils {

    public interface ErrorOperation{
        void make();
    }

    public static void assertNotError(ErrorOperation errorOperation){
        try{
            errorOperation.make();
        }catch (IllegalArgumentException e){
            throw e;
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
    }
}
