package test_core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration parameters for tests
 */
public class Configuration {

    private static Properties properties;

    private static final String PROPERTIES_RESOURCE = "configuration.properties";
    private static final String IS_SPRING_CONTEXT_TESTS_RUN = "run_spring_context_test";

    public static boolean isSpringContextTestRun(){
        return Boolean.valueOf(getProperties().getProperty(IS_SPRING_CONTEXT_TESTS_RUN));
    }

    private static Properties getProperties(){
        if(null == properties){
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            properties = new Properties();
            try(InputStream resourceStream = loader.getResourceAsStream(PROPERTIES_RESOURCE)) {
                properties.load(resourceStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return properties;
    }

}
