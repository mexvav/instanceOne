package core.entity.field.fields;

import core.Constants;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@SuppressWarnings("unused")
public class BooleanEntityField extends AbstractEntityField<Boolean> {

    @Autowired
    public BooleanEntityField() {
        super(Constants.EntityFieldType.BOOLEAN);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<Boolean> getFieldClass() {
        return Boolean.class;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BooleanEntityField)) return false;
        BooleanEntityField that = (BooleanEntityField) o;
        return Objects.equals(getCode(), that.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode(), getType());
    }
}
