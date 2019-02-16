package core.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import core.jpa.Constants;
import core.jpa.entity.fields.EntityField;
import core.jpa.interfaces.HasCode;
import core.jpa.interfaces.HasTitle;

import java.beans.Transient;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Description object for building entity class
 */
public class EntityClass implements HasCode, HasTitle {
    @JsonIgnore
    private String code;
    @JsonIgnore
    private String title;
    @JsonIgnore
    private Set<EntityField> fields;

    @SuppressWarnings("unused")
    EntityClass(String code) {
        setCode(code);
    }

    public EntityClass() {
    }

    @JsonIgnore
    public void addFields(EntityField... fields) {
        getFields().addAll(Arrays.asList(fields));
    }

    @JsonIgnore
    public void removeField(String code) {
        getFields().removeIf(field -> code.equals(field.getCode()));
    }

    @JsonIgnore
    public Set<String> getFieldCodes() {
        return getFields().stream().map(EntityField::getCode).collect(Collectors.toSet());
    }

    @JsonIgnore
    public String getCode() {
        return null == code ? Constants.EMPTY : code;
    }

    @JsonIgnore
    public void setCode(String entityName) {
        this.code = entityName.toLowerCase();
    }

    @JsonIgnore
    public Set<EntityField> getFields() {
        if (null == fields) {
            fields = Sets.newHashSet();
        }
        return fields;
    }

    @JsonIgnore
    public void setFields(Set<EntityField> fields) {
        this.fields = fields;
    }

    @JsonIgnore
    public String getTitle() {
        return title;
    }

    @JsonIgnore
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    @JsonIgnore
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntityClass)) return false;
        EntityClass that = (EntityClass) o;

        return Objects.equals(code, that.code)
                && Objects.equals(title, that.title)
                && Objects.equals(fields, that.fields);
    }

    @Override
    @JsonIgnore
    public int hashCode() {
        return Objects.hash(code, title, fields);
    }
}
