package test_core;

import core.Application;
import core.aspects.WithReloadSessionFactory;
import core.dao.DbDAO;
import core.dao.ObjectDAO;
import core.entity.EntityService;
import core.object.ObjectService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import test_core.extentions.HardClean;
import test_core.extentions.conditions.AssumeSpringContext;
import test_core.utils.SpringContextUtils;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        Application.class,
        AbstractSpringContextTest.TestContextConfiguration.class
})
@TestPropertySource(
        properties = {
                "datasource.url=jdbc:postgresql://127.0.0.1:5432/test",
                "datasource.driver-class-name=org.postgresql.Driver",
                "datasource.username=postgres",
                "datasource.password=postgres"
        })
@HardClean
@AssumeSpringContext
public class AbstractSpringContextTest {

    /**
     * Spring configuration for tests
     */
    @Configuration
    static class TestContextConfiguration {

        /**
         * Test utils for spring context tests
         */
        @Bean
        @SuppressWarnings("unused")
        public SpringContextUtils springContextUtils(EntityService entityService,
                                                     ObjectService objectService,
                                                     DbDAO dbDAO,
                                                     ObjectDAO objectDAO) {
            return new SpringContextUtils(entityService, objectService, dbDAO, objectDAO);
        }
    }
}


