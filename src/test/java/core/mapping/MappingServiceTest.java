package core.mapping;

import core.entity.EntityClass;
import core.entity.field.EntityField;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test_core.AbstractSpringContextTest;
import test_core.factories.EntityClassFactory;
import test_core.factories.EntityFieldFactory;
import test_core.utils.ExceptionUtils;

import java.util.Date;

class MappingServiceTest extends AbstractSpringContextTest {

    private MappingService mappingService;

    @Autowired
    MappingServiceTest(MappingService mappingService) {
        this.mappingService = mappingService;
    }

    /**
     * Test exception if suitable mapper not found
     */
    @Test
    void testMappingException() {
        ExceptionUtils.assertThrows(MappingException.class, () ->
                        mappingService.mapping(new Date(), Class.class),
                "Suitable mapper for mapping \"java.util.Date\" -> \"java.lang.Class\"");
    }

    /**
     * Testing mapping of empty {@link EntityClass} by {@link MappingService}
     */
    @Test
    void testMappingEmptyEntityClass() {
        EntityClass entityClass = EntityClassFactory.create();
        String json = mappingService.mapping(entityClass, String.class);

        String expectedJsonPattern = "{\"code\":\"%s\",\"fields\":[]}";
        String expectedJson = String.format(expectedJsonPattern, entityClass.getCode());
        Assertions.assertEquals(expectedJson, json);

        EntityClass mappedEntityClass = mappingService.mapping(json, EntityClass.class);
        Assertions.assertEquals(entityClass, mappedEntityClass);
    }

    /**
     * Testing mapping of {@link EntityClass} with all type of {@link EntityField} by {@link MappingService}
     */
    @Test
    void testMappingEntityClass() {
        EntityClass entityClass = EntityClassFactory.create();

        EntityField stringEntityField = EntityFieldFactory.stringEntityField();
        EntityField dateEntityField = EntityFieldFactory.dateEntityField();
        EntityField integerEntityField = EntityFieldFactory.integerEntityField();
        entityClass.addFields(stringEntityField, dateEntityField, integerEntityField);

        String json = mappingService.mapping(entityClass, String.class);
        EntityClass mappedEntityClass = mappingService.mapping(json, EntityClass.class);

        Assertions.assertEquals(entityClass, mappedEntityClass);
    }

    /**
     * Testing mapping of {@link EntityField} by {@link MappingService}
     */
    @Test
    void testMappingEntityField() {
        EntityField entityField = EntityFieldFactory.stringEntityField();
        String json = mappingService.mapping(entityField, String.class);

        String expectedJsonPattern = "{\"code\":\"%s\",\"type\":\"%s\"}";
        String expectedJson = String.format(expectedJsonPattern, entityField.getCode(), entityField.getType());
        Assertions.assertEquals(expectedJson, json);

        EntityField mappedEntityField = mappingService.mapping(json, EntityField.class);
        Assertions.assertEquals(entityField, mappedEntityField);

        entityField = EntityFieldFactory.dateEntityField();
        json = mappingService.mapping(entityField, String.class);

        mappedEntityField = mappingService.mapping(json, EntityField.class);
        Assertions.assertEquals(entityField, mappedEntityField);
    }
}
