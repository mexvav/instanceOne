package core.entity.field.fields;

import core.Constants;

import java.util.Date;
import java.util.Objects;

@SuppressWarnings("unused")
public class DateEntityField extends AbstractEntityField<Date> {

    public static final String type = Constants.EntityFieldType.DATE;

    public DateEntityField(){

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getType() {
        return type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<Date> getFieldClass() {
        return Date.class;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DateEntityField)) return false;
        DateEntityField that = (DateEntityField) o;
        return Objects.equals(getCode(), that.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode());
    }
}
