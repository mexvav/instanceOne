package core.entity.field.fields;

import core.Constants;
import core.entity.entities.Entity;

import java.util.Objects;

public class LinkEntityField extends AbstractEntityField<Entity> implements Linkable {

    private Class<Entity> entity;

    public LinkEntityField() {
        super(Constants.EntityFieldType.LINK);
    }

    @Override
    public Class<Entity> getFieldClass() {
        return entity;
    }

    @Override
    public void setLinkClass(Class<Entity> entity) {
        this.entity = entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LinkEntityField)) return false;
        LinkEntityField that = (LinkEntityField) o;
        return Objects.equals(entity, that.entity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity, getType());
    }
}
