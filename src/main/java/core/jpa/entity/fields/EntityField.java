package core.jpa.entity.fields;

import core.jpa.interfaces.HasCode;
import core.jpa.interfaces.HasTitle;

import java.beans.Transient;

public abstract class EntityField<C> implements HasCode, HasTitle {

    private String code;
    private String title;
    private boolean required = false;
    private boolean unique = false;

    public EntityField() {
    }

    public EntityField(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return code;
    }

    public void setTitle(String title) {
        this.title = title;
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
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();

    @Transient
    public abstract Class<C> getFieldClass();

    public abstract String getType();
}
