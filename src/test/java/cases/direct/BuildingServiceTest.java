package cases.direct;

import core.factories.EntityClassFactory;
import core.factories.EntityFieldFactory;
import core.jpa.entity.EntityClass;
import core.jpa.entity.building.BuildingService;
import core.jpa.entity.field.EntityField;
import core.jpa.entity.field.fields.DateEntityField;
import core.jpa.entity.field.fields.IntegerEntityField;
import core.jpa.entity.field.fields.StringEntityField;
import core.utils.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Date;

class BuildingServiceTest {

    private static BuildingService buildingService;

    @BeforeAll
    static void initialize() {
        buildingService = new BuildingService();
    }

    /**
     * Testing building of {@link EntityClass} by {@link BuildingService}
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
     * <li>build "entity" from "entityClass"</li>
     * </ol>
     *
     * <ol>
     * <b>Verifications:</b>
     * <li>is name of built "entity" equals capitalized "code" of "entityClass"</li>
     * <li>is built "entity" contains annotation {@link Entity}, value of attribute "name" equals "code" of "entityClass"</li>
     * <li>is built "entity" contains annotation {@link Table}, value of attribute "name" equals "code" of "entityClass"</li>
     * <li>is built "entity" contains fields with names from "entityClass" "entityFields"</li>
     * <li>is every field contains annotation {@link Column}</li>
     * </ol>
     */
    @Test
    void testEntityClassBuilding() {
        EntityClass entityClass = EntityClassFactory.create();

        EntityField stringEntityField = EntityFieldFactory.stringEntityField();
        EntityField dateEntityField = EntityFieldFactory.dateEntityField();
        EntityField integerEntityField = EntityFieldFactory.integerEntityField();
        entityClass.addFields(stringEntityField, dateEntityField, integerEntityField);

        Class<?> entity = Assertions.assertDoesNotThrow(
                () -> buildingService.building(entityClass), Constants.Error.BUILDING_EXCEPTION);
        Assertions.assertEquals(StringUtils.capitalize(entityClass.getCode()), entity.getSimpleName());

        Entity entityAnnotation = getAnnotation(entity, Entity.class);
        Assertions.assertEquals(entityClass.getCode(), entityAnnotation.name());

        Table tableAnnotation = getAnnotation(entity, Table.class);
        Assertions.assertEquals(entityClass.getCode(), tableAnnotation.name());

        checkField(entity, stringEntityField.getCode(), String.class);
        checkField(entity, dateEntityField.getCode(), Date.class);
        checkField(entity, integerEntityField.getCode(), Integer.class);
    }

    /**
     * Check field in entity class
     *
     * @param entity entity class
     * @param name   name of field
     * @param type   class of field
     * @throws AssertionError no such field
     */
    private void checkField(Class<?> entity, String name, Class type) {
        Field stringField = getField(entity, name);
        Assertions.assertTrue(
                stringField.getType().isAssignableFrom(type),
                Constants.Error.FIELD_TYPE);
        Column columnAnnotation = stringField.getAnnotation(Column.class);
        Assertions.assertNotNull(columnAnnotation,
                String.format(Constants.Error.NOT_FOUND_ANNOTATION, Column.class.getName()));
    }

    /**
     * Get field from entity
     *
     * @param entity entity class
     * @param name   name of field
     * @throws AssertionError no such field
     */
    private Field getField(Class<?> entity, String name) {
        try {
            return entity.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            throw new AssertionError(e.getMessage());
        }
    }

    /**
     * Get field from entity
     *
     * @param entity          entity class
     * @param annotationClass annotation class
     * @throws AssertionError no such annotation
     */
    private <A extends Annotation> A getAnnotation(Class<?> entity, Class<A> annotationClass) {
        A annotation = entity.getAnnotation(annotationClass);
        Assertions.assertNotNull(annotation, String.format(Constants.Error.NOT_FOUND_ANNOTATION, annotationClass.getName()));
        return annotation;
    }
}
