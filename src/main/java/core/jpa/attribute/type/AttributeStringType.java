package core.jpa.attribute.type;

public class AttributeStringType implements AttributeType {
    public static final String code = "string";

    @Override
    public String getTypeCode() {
        return code;
    }

    @Override
    public Class<?> getAttributeClass() {
        return String.class;
    }
}
