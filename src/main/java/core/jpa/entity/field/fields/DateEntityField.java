package core.jpa.entity.field.fields;

import core.jpa.Constants;

import java.util.Date;
import java.util.Objects;

@SuppressWarnings("unused")
public class DateEntityField extends SimpleEntityField<Date> {

    public static final String code = Constants.EntityFieldType.DATE;

    public DateEntityField(){

    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public Class<Date> getFieldClass() {
        return Date.class;
    }

    @Override
    public String getType() {
        return code;
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
