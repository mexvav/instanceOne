package core.jpa.entity.attribute;

import core.jpa.entity.attribute.type.AttributeType;
import core.jpa.interfaces.HasCode;

import java.util.Objects;

public class Attribute implements HasCode {
    private AttributeType type;
    private String code;
    private boolean required = false;
    private boolean unique = false;

    public Attribute(String code) {
        this.code = code;
    }

    public AttributeType getType() {
        return type;
    }

    public void setType(AttributeType type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attribute)) return false;
        Attribute attribute = (Attribute) o;
        return required == attribute.required &&
                unique == attribute.unique &&
                Objects.equals(type, attribute.type) &&
                Objects.equals(code, attribute.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, code, required, unique);
    }
}
