package core.jpa.entity.attribute.type;

import core.jpa.Constants;

public class AttributeIntegerType extends AttributeSimpleType {
    public static final String code = Constants.AttributeType.INTEGER;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public Class<?> getAttributeClass() {
        return Integer.class;
    }
}
