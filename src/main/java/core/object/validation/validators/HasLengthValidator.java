package core.object.validation.validators;

import core.Constants;
import core.entity.field.EntityField;
import core.interfaces.HasLength;
import core.object.validation.ValidationException;

import java.util.Arrays;

public class HasLengthValidator extends AbstractValidation<EntityField> {

    @Override
    public boolean isSuitable(EntityField entityField) {
        return Arrays.asList(entityField.getClass().getInterfaces()).contains(HasLength.class);
    }

    @Override
    public Class<EntityField> getSuitableClass() {
        return EntityField.class;
    }

    @Override
    public void validation(EntityField entityField, Object object) {
        if (null == object) {
            return;
        }
        int length = ((HasLength) entityField).getLength();
        if (length == Constants.HasLength.DEFAUIT) {
            return;
        }
        int actualLength = Constants.HasLength.DEFAUIT;
        if (object instanceof String) {
            actualLength = ((String) object).length();
        }
        if (actualLength == Constants.HasLength.DEFAUIT) {
            throw new ValidationException(ValidationException.ExceptionCauses.VALUE_IS_TOO_LONG,
                    String.valueOf(length));
        }
        if (actualLength > length) {
            throw new ValidationException(ValidationException.ExceptionCauses.VALUE_IS_TOO_LONG,
                    String.valueOf(length));
        }
    }
}
