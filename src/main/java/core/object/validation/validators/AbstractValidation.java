package core.object.validation.validators;

import core.object.validation.ValidationService;

public abstract class AbstractValidation<O> implements Validator<O> {

    private ValidationService validation;

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(ValidationService validation) {
        this.validation = validation;
        validation.initSuitableObject(this);
    }
}
