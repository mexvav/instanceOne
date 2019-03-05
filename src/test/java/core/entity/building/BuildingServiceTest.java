package core.entity.building;

import core.entity.EntityClass;
import core.entity.field.EntityField;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import test_core.AbstractSpringContextTest;
import test_core.factories.EntityClassFactory;
import test_core.factories.EntityFieldFactory;
import test_core.utils.Constants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Date;

class BuildingServiceTest extends AbstractSpringContextTest {

    private BuildingService buildingService;

    @Autowired
    BuildingServiceTest(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    /**
     * Testing building of {@link EntityClass} by {@link BuildingService}
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
