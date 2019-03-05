package core.object.validation.validators;

import core.entity.field.EntityField;
import core.object.validation.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test_core.AbstractSpringContextTest;
import test_core.factories.EntityFieldFactory;
import test_core.factories.ObjectFactory;
import test_core.utils.SpringContextUtils;

class RequiredValidatorTest extends AbstractSpringContextTest {

    private RequiredValidator validator;

    @Autowired
    RequiredValidatorTest(SpringContextUtils utils, RequiredValidator validator) {
        this.validator = validator;
    }

    @Test
    void testSuccessValidationForStringField() {
        EntityField entityField = EntityFieldFactory.stringEntityField();
        Object value = ObjectFactory.createValueForField(entityField);
        validator.validation(entityField, value);
    }

    @Test
    void testFailValidationForStringField() {
        EntityField entityField = EntityFieldFactory.stringEntityField();
        Assertions.assertThrows(ValidationException.class, () ->
                validator.validation(entityField, null)
        );
    }

    @Test
    void testSuccessValidationForDateField() {
        EntityField entityField = EntityFieldFactory.dateEntityField();
        Object value = ObjectFactory.createValueForField(entityField);
        validator.validation(entityField, value);
    }

    @Test
    void testFailValidationForDateField() {
        EntityField entityField = EntityFieldFactory.dateEntityField();
        Assertions.assertThrows(ValidationException.class, () ->
                validator.validation(entityField, null)
        );
    }

    @Test
    void testSuccessValidationForIntegerField() {
        EntityField entityField = EntityFieldFactory.integerEntityField();
        Object value = ObjectFactory.createValueForField(entityField);
        validator.validation(entityField, value);
    }

    @Test
    void testFailValidationForIntegerField() {
        EntityField entityField = EntityFieldFactory.integerEntityField();
        Assertions.assertThrows(ValidationException.class, () ->
                validator.validation(entityField, null)
        );
    }
}
