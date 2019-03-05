package core.object.validation.validators;

import core.entity.field.EntityField;
import core.object.validation.ValidationException;
import core.object.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequiredValidator extends AbstractValidation<EntityField> {

    @Autowired
    RequiredValidator(ValidationService service) {
        super(service, EntityField.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSuitable(EntityField field) {
        return field.isRequired();
    }

    /**
     * Validate field value is not null
     *
     * @param field the validation field
     * @param value the validation value
     */
    @Override
    public void validation(EntityField field, Object value) {
        if (null == value) {
            throw new ValidationException(ValidationException.ExceptionCauses.VALUE_IS_REQUIRED, field.getCode());
        }
    }
}