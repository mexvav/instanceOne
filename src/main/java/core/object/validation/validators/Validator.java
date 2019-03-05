package core.object.validation.validators;

import core.object.validation.ValidationService;
import core.utils.register.RegisteredObjectWithClass;

public interface Validator<O> extends RegisteredObjectWithClass<O, ValidationService> {

    /**
     * Is validator suitable for object
     *
     * @param object the verifiable object
     */
    boolean isSuitable(O object);

    /**
     * Validate value for {@link core.entity.field.EntityField}
     *
     * @param value the verifiable value
     */
    void validation(O object, Object value);
}