package core.jpa.entity.fields.types;

import core.jpa.Constants;
import core.jpa.interfaces.HasLength;

import java.util.Objects;

@SuppressWarnings("unused")
public class StringEntityFieldType extends SimpleEntityFieldType implements HasLength {

    public static final String code = Constants.EntityFieldType.STRING;

    private int length = Constants.HasLength.DEFAUIT;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public Class<?> getFieldClass() {
        return String.class;
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
        if (!(o instanceof StringEntityFieldType)) return false;
        StringEntityFieldType that = (StringEntityFieldType) o;
        return length == that.length
                && Objects.equals(getCode(), that.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(length, code);
    }
}
