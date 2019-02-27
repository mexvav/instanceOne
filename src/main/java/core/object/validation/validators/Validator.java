package core.object.validation.validators;

import core.object.validation.ValidationService;
import core.utils.suitable.SuitableObjectByClass;

public interface Validator<O> extends SuitableObjectByClass<O, ValidationService> {

    /**
     * Is validator suitable for object
     *
     * @param object
     */
    boolean isSuitable(O object);

    /**
     * Validate value for {@link core.entity.field.EntityField}
     *
     * @param value the value
     */
    void validation(O object, Object value);
}