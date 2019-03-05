package core.object.validation.validators;

import core.Constants;
import core.entity.field.EntityField;
import core.interfaces.HasLength;
import core.object.validation.ValidationException;
import core.object.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class HasLengthValidator extends AbstractValidation<EntityField> {

    @Autowired
    HasLengthValidator(ValidationService service) {
        super(service, EntityField.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSuitable(EntityField entityField) {
        return Arrays.asList(entityField.getClass().getInterfaces()).contains(HasLength.class);
    }

    /**
     * Validate length for field
     *
     * @param field the validation field
     * @param value the validation value
     */
    @Override
    public void validation(EntityField field, Object value) {
        if (null == value) {
            return;
        }
        int length = ((HasLength) field).getLength();
        if (length == Constants.HasLength.DEFAUIT) {
            return;
        }
        int actualLength = Constants.HasLength.DEFAUIT;
        if (value instanceof String) {
            actualLength = ((String) value).length();
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
