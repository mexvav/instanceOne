package core.entity;

import com.google.common.collect.Sets;
import core.Constants;
import core.entity.field.EntityField;
import core.interfaces.HasCode;

import java.beans.Transient;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Description object for building entity class
 */
public class EntityClass implements HasCode {

    private String code;
    private Set<EntityField> fields;

    @SuppressWarnings("unused")
    EntityClass(String code) {
        setCode(code);
    }

    public EntityClass() {
    }

    /**
     * Add field blank {@link EntityField}
     *
     * @param fields field blanks
     */
    public void addFields(EntityField... fields) {
        getFields().addAll(Arrays.asList(fields));
    }

    /**
     * Remove field blank {@link EntityField}
     *
     * @param code code of {@link EntityField}
     */
    public void removeField(String code) {
        getFields().removeIf(field -> code.equals(field.getCode()));
    }

    /**
     * Get all fields code. {@link EntityField#getCode()}
     */
    @Transient
    public Set<String> getFieldCodes() {
        return getFields().stream().map(EntityField::getCode).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    public String getCode() {
        return null == code ? Constants.EMPTY : code;
    }

    /**
     * {@inheritDoc}
     */
    public void setCode(String entityName) {
        this.code = entityName.toLowerCase();
    }

    /**
     * Get all fields
     */
    public Set<EntityField> getFields() {
        if (null == fields) {
            fields = Sets.newHashSet();
        }
        return fields;
    }

    /**
     * Set all fields
     */
    public void setFields(Set<EntityField> fields) {
        this.fields = fields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntityClass)) return false;
        EntityClass that = (EntityClass) o;

        return Objects.equals(code, that.code)
                && Objects.equals(fields, that.fields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, fields);
    }
}
