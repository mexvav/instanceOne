package core.jpa.entity.fields;

public abstract class SimpleEntityField<C> extends EntityField<C> {
    public abstract boolean equals(Object other);
    public abstract int hashCode();

}
