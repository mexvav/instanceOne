package core.jpa.attribute;

import core.jpa.attribute.type.AttributeType;

public class Attribute {
    private AttributeType type;
    private String code;

    public Attribute(String code) {
        this.code = code;
    }

    boolean required = false;

    boolean unique = false;

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
}
