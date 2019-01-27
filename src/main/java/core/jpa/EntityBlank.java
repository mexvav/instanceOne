package core.jpa;

import core.jpa.attribute.Attribute;

import java.util.Collection;

/**
 * Params for generate entity
 */
public class EntityBlank {
    private String code;
    private String title;
    private String tablePrefix;
    private String tablePostfix;
    private Collection<Attribute> attributes;

    EntityBlank(String code) {
        this.code = code;
    }

    public EntityBlank() {
    }

    public String getTablePrefix() {
        return null == tablePrefix ? "" : tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public String getTablePostfix() {
        return null == tablePostfix ? "" : tablePostfix;
    }

    public void setTablePostfix(String tablePostfix) {
        this.tablePostfix = tablePostfix;
    }

    public String getCode() {
        return null == code ? "" : code;
    }

    public void setCode(String entityName) {
        this.code = entityName;
    }

    public Collection<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Collection<Attribute> attributes) {
        this.attributes = attributes;
    }

    public String getTableName() {
        return getTablePrefix() + getCode() + getTablePostfix();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}