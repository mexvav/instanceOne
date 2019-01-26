package core.jpa.attribute.type;

public interface AttributeType {
    String getTypeCode();

    Class<?> getAttributeClass();
}
