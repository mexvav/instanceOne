package core.object.validation;

import core.entity.field.EntityField;
import core.object.validation.validators.RequiredValidator;
import core.object.validation.validators.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test_core.AbstractSpringContextTest;
import test_core.factories.EntityFieldFactory;

import java.util.Set;

class ValidationServiceTest extends AbstractSpringContextTest {

    private ValidationService validationService;

    @Autowired
    ValidationServiceTest(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Test
    void testGetValidatorsForRequiredField() {
        EntityField entityField = EntityFieldFactory.stringEntityField();
        entityField.setRequired(true);

        Set<Validator> validators = validationService.getValidators(entityField);
        boolean contains = validators.stream().anyMatch(validator -> validator.getClass().equals(RequiredValidator.class));
        Assertions.assertTrue(contains);
    }

    @Test
    void testGetValidatorsForNotRequiredField() {
        EntityField entityField = EntityFieldFactory.stringEntityField();
        entityField.setRequired(false);

        Set<Validator> validators = validationService.getValidators(entityField);
        boolean contains = validators.stream().anyMatch(validator -> validator.getClass().equals(RequiredValidator.class));
        Assertions.assertFalse(contains);
    }
}
