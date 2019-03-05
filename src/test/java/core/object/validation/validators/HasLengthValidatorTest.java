package core.object.validation.validators;

import core.entity.field.EntityField;
import core.interfaces.HasLength;
import core.object.validation.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test_core.AbstractSpringContextTest;
import test_core.factories.EntityFieldFactory;
import test_core.factories.ObjectFactory;
import test_core.utils.EntityFieldUtils;
import test_core.utils.RandomUtils;

class HasLengthValidatorTest extends AbstractSpringContextTest {

    private HasLengthValidator validator;

    @Autowired
    HasLengthValidatorTest(HasLengthValidator validator) {
        this.validator = validator;
    }

    @Test
    void testSuccessValidation() {
        EntityField entityField = EntityFieldFactory.stringEntityField();
        Object value = ObjectFactory.createValueForField(entityField);
        Assertions.assertDoesNotThrow(() ->
                validator.validation(entityField, value)
        );
    }

    @Test
    void testFailValidation() {
        EntityField entityField = EntityFieldFactory.stringEntityField();
        ((HasLength) entityField).setLength(1);
        Object value = RandomUtils.generateEnglishString(2);
        Assertions.assertThrows(ValidationException.class, () ->
                validator.validation(entityField, value)
        );
    }

    @Test
    void testSuccessValidationIfLengthNotSet() {
        EntityField entityField = EntityFieldFactory.stringEntityField();
        int length = EntityFieldUtils.getLength(entityField);
        String value = RandomUtils.generateEnglishString(length + 1);
        Assertions.assertDoesNotThrow(() ->
                validator.validation(entityField, value)
        );
    }

    @Test
    void testSuccessValidationIfValueIsNull() {
        EntityField entityField = EntityFieldFactory.stringEntityField();
        Assertions.assertDoesNotThrow(() ->
                validator.validation(entityField, null)
        );
    }
}
