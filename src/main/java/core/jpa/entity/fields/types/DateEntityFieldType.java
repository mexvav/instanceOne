package core.jpa.entity.fields.types;

import core.jpa.Constants;

import java.util.Date;
import java.util.Objects;

@SuppressWarnings("unused")
public class DateEntityFieldType extends SimpleEntityFieldType {

    public static final String code = Constants.EntityFieldType.DATE;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public Class<?> getFieldClass() {
        return Date.class;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DateEntityFieldType)) return false;
        DateEntityFieldType that = (DateEntityFieldType) o;
        return Objects.equals(getCode(), that.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
