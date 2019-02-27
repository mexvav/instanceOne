package core.object.validation.validators;

import core.entity.field.EntityField;
import core.object.validation.ValidationException;

public class RequiredValidator extends AbstractValidation<EntityField> {

    @Override
    public boolean isSuitable(EntityField field) {
        return field.isRequired();
    }

    @Override
    public Class<EntityField> getSuitableClass() {
        return EntityField.class;
    }

    @Override
    public void validation(EntityField field, Object object) {
        if (null == object) {
            throw new ValidationException(ValidationException.ExceptionCauses.VALUE_IS_REQUIRED, field.getCode());
        }
    }
}