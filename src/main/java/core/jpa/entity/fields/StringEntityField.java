package core.jpa.entity.fields;

import core.jpa.Constants;
import core.jpa.interfaces.HasLength;

import java.util.Objects;

@SuppressWarnings("unused")
public class StringEntityField extends SimpleEntityField<String> implements HasLength {

    public static final String code = Constants.EntityFieldType.STRING;

    private int length = Constants.HasLength.DEFAUIT;

    @Override
    public Class<String> getFieldClass() {
        return String.class;
    }

    @Override
    public String getType() {
        return code;
    }

    @Override
    public int getLength() {
        return length;
    }

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
        return Objects.hash(length, code);
    }
}
