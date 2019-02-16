package core.jpa.entity.fields.types;

import core.jpa.Constants;

import java.util.Objects;

@SuppressWarnings("unused")
public class IntegerEntityFieldType extends SimpleEntityFieldType {

    public static final String code = Constants.EntityFieldType.INTEGER;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public Class<?> getFieldClass() {
        return Integer.class;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntegerEntityFieldType)) return false;
        IntegerEntityFieldType that = (IntegerEntityFieldType) o;
        return Objects.equals(getCode(), that.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
