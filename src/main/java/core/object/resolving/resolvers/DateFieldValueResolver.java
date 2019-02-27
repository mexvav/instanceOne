package core.object.resolving.resolvers;

import core.Constants;
import core.entity.field.fields.DateEntityField;
import core.object.resolving.ResolvingException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@SuppressWarnings("unused")
public class DateFieldValueResolver extends AbstractFieldValueResolver<Date, DateEntityField> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.EntityValue.DATE_TEMPLATE, Locale.ENGLISH);

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<DateEntityField> getSuitableClass() {
        return DateEntityField.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date resolve(Object object) {
        if (null == object) {
            return null;
        }
        if (object instanceof Date) {
            return (Date) object;
        }
        if (object instanceof String) {
            try {
                return dateFormat.parse((String) object);
            } catch (ParseException e) {
                throw new ResolvingException(
                        ResolvingException.ExceptionCauses.DATE_RESOLVING_IS_FAILED,
                        (String) object, Constants.EntityValue.DATE_TEMPLATE
                );
            }
        }
        throw new ResolvingException(
                ResolvingException.ExceptionCauses.RESOLVING_IS_FAILED, object.toString(), getSuitableClass().getName());
    }
}