package core.object.validation;

import core.entity.field.EntityField;
import core.object.validation.validators.RequiredValidator;
import core.object.validation.validators.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import test_core.factories.EntityFieldFactory;

import java.util.Set;

class ValidationServiceTest {

    private static ValidationService validationService;

    @BeforeAll
    static void initialize() {
        validationService = new ValidationService();
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
