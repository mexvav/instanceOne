package spring_context_tests;

import core.Application;
import core.assertions.SpringContextAssertions;
import core.extentions.HardClean;
import core.extentions.conditions.AssumeSpringContext;
import core.jpa.dao.EntityDAO;
import core.jpa.entity.EntityService;
import core.jpa.object.ObjectService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Application.class})
@TestPropertySource(
        properties = {
                "datasource.url=jdbc:postgresql://127.0.0.1:5432/test",
                "datasource.driver-class-name=org.postgresql.Driver",
                "datasource.username=postgres",
                "datasource.password=postgres"
        })
@HardClean
@AssumeSpringContext
class SpringContextAbstractTest {

    protected SpringContextAssertions testUtils;

    @Autowired
    SpringContextAbstractTest(EntityService entityService,
                              ObjectService objectService,
                              EntityDAO entityDAO){
        testUtils = new SpringContextAssertions(entityService, objectService, entityDAO);
    }
}
