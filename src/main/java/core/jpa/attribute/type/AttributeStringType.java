package core.jpa.attribute.type;

public class AttributeStringType extends AttributeSimpleType {
    public static final String code = "string";

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public Class<?> getAttributeClass() {
        return String.class;
    }
}
