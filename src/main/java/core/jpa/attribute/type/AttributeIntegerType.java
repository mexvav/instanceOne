package core.jpa.attribute.type;

public class AttributeIntegerType extends AttributeSimpleType {
    public static final String code = "integer";

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public Class<?> getAttributeClass() {
        return Integer.class;
    }
}
