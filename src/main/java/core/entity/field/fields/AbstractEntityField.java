package core.entity.field.fields;

import core.entity.field.EntityField;

import java.beans.Transient;

public abstract class AbstractEntityField<C> implements EntityField<C> {

    private String code;
    private boolean required = false;
    private boolean unique = false;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract String getType();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRequired() {
        return required;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRequired(boolean required) {
        this.required = required;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isUnique() {
        return unique;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();

    /**
     * {@inheritDoc}
     */
    @Transient
    public abstract Class<C> getFieldClass();
}
