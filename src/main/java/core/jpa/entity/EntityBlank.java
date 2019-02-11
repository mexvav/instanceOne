package core.jpa.entity;

import com.google.common.collect.Sets;
import core.jpa.entity.attribute.Attribute;
import core.jpa.interfaces.HasCode;
import core.jpa.interfaces.HasTitle;

import java.util.Objects;
import java.util.Set;

/**
 * Params for generate entity
 */
public class EntityBlank implements HasCode, HasTitle {
    private String code;
    private String title;
    private Set<Attribute> attributes;

    @SuppressWarnings("unused")
    EntityBlank(String code) {
        setCode(code);
    }

    public EntityBlank() {
    }

    public String getCode() {
        return null == code ? "" : code;
    }

    public void setCode(String entityName) {
        this.code = entityName.toLowerCase();
    }

    public Set<Attribute> getAttributes() {
        if (null == attributes) {
            attributes = Sets.newHashSet();
        }
        return attributes;
    }

    public void setAttributes(Set<Attribute> attributes) {
        this.attributes = attributes;
    }

    public String getTableName() {
        return getCode();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntityBlank)) return false;
        EntityBlank that = (EntityBlank) o;

        return Objects.equals(code, that.code) &&
                Objects.equals(title, that.title) &&
                Objects.equals(attributes, that.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, title, attributes);
    }
}