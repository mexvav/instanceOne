package core.entity.field.fields;

import core.Constants;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@SuppressWarnings("unused")
public class IntegerEntityField extends AbstractEntityField<Integer> {

    @Autowired
    public IntegerEntityField() {
        super(Constants.EntityFieldType.INTEGER);
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
        return Objects.hash(getCode(), getType());
    }
}
