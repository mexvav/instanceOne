package core.jpa.entity.fields;

import core.jpa.entity.fields.types.EntityFieldType;
import core.jpa.interfaces.HasCode;

import java.util.Objects;

public class EntityField implements HasCode {

    private EntityFieldType type;
    private String code;
    private boolean required = false;
    private boolean unique = false;

    public EntityField(String code) {
        this.code = code;
    }

    public EntityFieldType getType() {
        return type;
    }

    public void setType(EntityFieldType type) {
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
        if (!(o instanceof EntityField)) return false;
        EntityField entityField = (EntityField) o;
        return required == entityField.required
                && unique == entityField.unique
                && Objects.equals(type, entityField.type)
                && Objects.equals(code, entityField.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, code, required, unique);
    }
}
