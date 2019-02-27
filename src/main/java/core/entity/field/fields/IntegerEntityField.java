package core.entity.field.fields;

import core.Constants;

import java.util.Objects;

@SuppressWarnings("unused")
public class IntegerEntityField extends AbstractEntityField<Integer> {

    public static final String type = Constants.EntityFieldType.INTEGER;

    public IntegerEntityField(){

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
