package core.object.validation.validators;

import core.entity.field.EntityField;
import core.object.validation.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import test_core.factories.EntityFieldFactory;
import test_core.factories.ObjectFactory;

class RequiredValidatorTest {

    @Test
    void testSuccessValidationForStringField() {
        EntityField entityField = EntityFieldFactory.stringEntityField();
        Object value = ObjectFactory.createValueForField(entityField);
        RequiredValidator validator = new RequiredValidator();
        validator.validation(entityField, value);
    }

    @Test
    void testFailValidationForStringField() {
        EntityField entityField = EntityFieldFactory.stringEntityField();
        RequiredValidator validator = new RequiredValidator();
        Assertions.assertThrows(ValidationException.class, () ->
                validator.validation(entityField, null)
        );
    }

    @Test
    void testSuccessValidationForDateField() {
        EntityField entityField = EntityFieldFactory.dateEntityField();
        Object value = ObjectFactory.createValueForField(entityField);
        RequiredValidator validator = new RequiredValidator();
        validator.validation(entityField, value);
    }

    @Test
    void testFailValidationForDateField() {
        EntityField entityField = EntityFieldFactory.dateEntityField();
        RequiredValidator validator = new RequiredValidator();
        Assertions.assertThrows(ValidationException.class, () ->
                validator.validation(entityField, null)
        );
    }

    @Test
    void testSuccessValidationForIntegerField() {
        EntityField entityField = EntityFieldFactory.integerEntityField();
        Object value = ObjectFactory.createValueForField(entityField);
        RequiredValidator validator = new RequiredValidator();
        validator.validation(entityField, value);
    }

    @Test
    void testFailValidationForIntegerField() {
        EntityField entityField = EntityFieldFactory.integerEntityField();
        RequiredValidator validator = new RequiredValidator();
        Assertions.assertThrows(ValidationException.class, () ->
                validator.validation(entityField, null)
        );
    }
}
