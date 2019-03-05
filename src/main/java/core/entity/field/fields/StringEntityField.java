package core.entity.field.fields;

import core.Constants;
import core.interfaces.HasLength;

import java.util.Objects;

@SuppressWarnings("unused")
public class StringEntityField extends AbstractEntityField<String> implements HasLength {

    private int length = Constants.HasLength.DEFAUIT;

    public StringEntityField() {
        super(Constants.EntityFieldType.STRING);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<String> getFieldClass() {
        return String.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLength() {
        return length;
    }

    /**
     * {@inheritDoc}
     */
    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StringEntityField)) return false;
        StringEntityField that = (StringEntityField) o;
        return length == that.length
                && Objects.equals(getCode(), that.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode(), getType(), getLength());
    }
}
