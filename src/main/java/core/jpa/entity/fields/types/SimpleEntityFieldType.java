package core.jpa.entity.fields.types;

public abstract class SimpleEntityFieldType implements EntityFieldType {
    public abstract boolean equals(Object other);
    public abstract int hashCode();
}
