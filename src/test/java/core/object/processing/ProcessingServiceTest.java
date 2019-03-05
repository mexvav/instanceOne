package core.object.processing;

import com.google.common.collect.Maps;
import core.entity.EntityClass;
import core.entity.EntityService;
import core.object.ObjectServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test_core.AbstractSpringContextTest;
import test_core.factories.EntityClassFactory;
import test_core.utils.RandomUtils;

import java.util.Map;

class ProcessingServiceTest extends AbstractSpringContextTest {

    private ProcessingService processingService;
    private EntityService entityService;

    @Autowired
    ProcessingServiceTest(ProcessingService processingService,
                          EntityService entityService) {
        this.processingService = processingService;
        this.entityService = entityService;
    }

    @Test
    void testValidationParams() {
        EntityClass entityClass = EntityClassFactory.create();
        entityService.createEntity(entityClass);

        String code = RandomUtils.getCode();
        String value = RandomUtils.getTitle();

        Map<String, Object> params = Maps.newHashMap();
        params.put(code, value);

        ProcessorContext context = ContextFactory.create(entityClass.getCode(), params);
        Assertions.assertThrows(ObjectServiceException.class,
                () -> processingService.processing(context));
    }
}
