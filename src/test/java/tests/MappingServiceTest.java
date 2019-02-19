package tests;

import core.factories.EntityClassFactory;
import core.factories.EntityFieldFactory;
import core.jpa.entity.EntityClass;
import core.jpa.entity.field.EntityField;
import core.jpa.entity.field.fields.DateEntityField;
import core.jpa.entity.field.fields.IntegerEntityField;
import core.jpa.entity.field.fields.StringEntityField;
import core.jpa.mapping.MappingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class MappingServiceTest {

    private static MappingService mappingService;

    @BeforeAll
    static void initialize() {
        mappingService = new MappingService();
    }

    /**
     * Testing mapping of empty {@link EntityClass} by {@link MappingService}
     *
     * <ol>
     * <b>Actions:</b>
     * <li>create "entityClass" of {@link EntityClass}</li>
     * <li>mapping "entityClass" to "json"</li>
     * </ol>
     *
     * <ol>
     * <b>Verifications:</b>
     * <li>is "json" equals "{"code":"%s","title":"%s","fields":[]}"</li>
     * </ol>
     *
     * <ol>
     * <b>Actions:</b>
     * <li>mapping "json" to "mappedEntityClass"</li>
     * </ol>
     *
     * <ol>
     * <b>Verifications:</b>
     * <li>is "entityClass" equals "mappedEntityClass"</li>
     * </ol>
     */
    @Test
    void testMappingEmptyEntityClass() {
        EntityClass entityClass = EntityClassFactory.create();
        String json = mappingService.mapping(entityClass, String.class);

        String expectedJsonPattern = "{\"code\":\"%s\",\"title\":\"%s\",\"fields\":[]}";
        String expectedJson = String.format(expectedJsonPattern, entityClass.getCode(), entityClass.getTitle());
        Assertions.assertEquals(expectedJson, json);

        EntityClass mappedEntityClass = mappingService.mapping(json, EntityClass.class);
        Assertions.assertEquals(entityClass, mappedEntityClass);
    }

    /**
     * Testing mapping of {@link EntityField} by {@link MappingService}
     *
     * <ol>
     * <b>Actions:</b>
     * <li>create "entityField" with type {@link StringEntityField}</li>
     * <li>mapping "entityClass" to "json"</li>
     * </ol>

     * <ol>
     * <b>Verifications:</b>
     * <li>is "json" equals "{"code":"%s","type":{"length":0,"code":"%s"}"</li>
     * </ol>
     *
     * <ol>
     * <b>Actions:</b>
     * <li>mapping "json" to "mappedEntityClass"</li>
     * </ol>
     *
     * <ol>
     * <b>Verifications:</b>
     * <li>is "entityClass" equals "mappedEntityClass"</li>
     * </ol>
     *
     */
    //@Test
    void testMappingEntityField() {
        EntityField entityField = EntityFieldFactory.stringEntityField();
        String json = mappingService.mapping(entityField, String.class);

        String expectedJsonPattern = "{\"code\":\"%s\",\"title\":\"%s\",\"required\":false,\"unique\":false,\"length\":0,\"type\":\"%s\"}";
        String expectedJson = String.format(expectedJsonPattern, entityField.getCode(), entityField.getCode(), entityField.getType());
        Assertions.assertEquals(expectedJson, json);

        EntityField mappedEntityField = mappingService.mapping(json, EntityField.class);
        Assertions.assertEquals(entityField, mappedEntityField);

        entityField = EntityFieldFactory.dateEntityField();
        json = mappingService.mapping(entityField, String.class);

        expectedJsonPattern = "{\"code\":\"%s\",\"type\":\"%s\"}";
        expectedJson = String.format(expectedJsonPattern, entityField.getCode(), entityField.getType());
        Assertions.assertEquals(expectedJson, json);

        mappedEntityField = mappingService.mapping(json, EntityField.class);
        Assertions.assertEquals(entityField, mappedEntityField);
    }

    /**
     * Testing mapping of {@link EntityClass} with all type of {@link EntityField} by {@link MappingService}
     *
     * <ol>
     * <b>Actions:</b>
     * <li>create "entityClass" of {@link EntityClass}</li>
     * <li>add "entityFields" of {@link EntityField} to "entityClass", field types:
     * <ul>
     * <li>{@link StringEntityField}</li>
     * <li>{@link DateEntityField}</li>
     * <li>{@link IntegerEntityField}</li>
     * </ul>
     * </li>
     * <li>mapping "entityClass" to "json"</li>
     * <li>mapping "json" to "mappedEntityClass"</li>
     * </ol>
     *
     * <ol>
     * <b>Verifications:</b>
     * <li>is "entityClass" equals "mappedEntityClass"</li>
     * </ol>
     */
    //@Test
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
}
