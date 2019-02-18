package core.jpa.entity.fields;

import core.jpa.Constants;

import java.util.Objects;

@SuppressWarnings("unused")
public class IntegerEntityField extends SimpleEntityField<Integer> {

    public static final String code = Constants.EntityFieldType.INTEGER;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getType() {
        return code;
    }

    @Override
    public Class<Integer> getFieldClass() {
        return Integer.class;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntegerEntityField)) return false;
        IntegerEntityField that = (IntegerEntityField) o;
        return Objects.equals(getCode(), that.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode());
    }
}
