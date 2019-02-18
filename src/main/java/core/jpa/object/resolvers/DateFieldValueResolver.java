package core.jpa.object.resolvers;

import core.jpa.Constants;
import core.jpa.entity.fields.types.DateEntityFieldType;
import core.jpa.object.ObjectServiceException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@SuppressWarnings("unused")
public class DateFieldValueResolver implements EntityFieldValueResolver<Date, DateEntityFieldType> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.EntityValue.DATE_TEMPLATE, Locale.ENGLISH);

    @Override
    public Class<DateEntityFieldType> getSuitableType() {
        return DateEntityFieldType.class;
    }

    @Override
    public Date resolve(Object object) {
        if (object instanceof Date) {
            return (Date) object;
        }
        if (object instanceof String) {

            try {
                return dateFormat.parse((String) object);
            } catch (ParseException e) {
                throw new ObjectServiceException(
                        ObjectServiceException.ExceptionCauses.DATE_RESOLVING_IS_FAILED,
                        (String) object, Constants.EntityValue.DATE_TEMPLATE
                );
            }
        }
        throw new ObjectServiceException(
                ObjectServiceException.ExceptionCauses.RESOLVING_IS_FAILED, getSuitableType().getName(), object.getClass().getName());
    }
}