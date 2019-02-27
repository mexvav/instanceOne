package test_core;

import core.Application;
import core.dao.DbDAO;
import core.dao.ObjectDAO;
import core.entity.EntityService;
import core.object.ObjectService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import test_core.extentions.HardClean;
import test_core.utils.SpringContextUtils;

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
//@AssumeSpringContext
public class SpringContextAbstractTest {

    protected SpringContextUtils utils;

    @Autowired
    public SpringContextAbstractTest(EntityService entityService,
                                     ObjectService objectService,
                                     DbDAO dbDAO,
                                     ObjectDAO objectDAO) {
        utils = new SpringContextUtils(entityService, objectService, dbDAO, objectDAO);
    }
}
