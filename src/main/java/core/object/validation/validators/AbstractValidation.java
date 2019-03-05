package core.object.validation.validators;

import core.object.validation.ValidationService;

public abstract class AbstractValidation<O> implements Validator<O> {

    private ValidationService validationService;

    private Class<O> suitableClass;

    public AbstractValidation(ValidationService validationService, Class<O> suitableClass) {
        this.validationService = validationService;
        this.suitableClass = suitableClass;
        validationService.register(this);
    }

    @Override
    public Class<O> getSuitableClass() {
        return suitableClass;
    }

    protected ValidationService getValidationService() {
        return validationService;
    }
}
