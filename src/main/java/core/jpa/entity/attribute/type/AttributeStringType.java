package core.jpa.entity.attribute.type;

import core.jpa.Constants;

public class AttributeStringType extends AttributeSimpleType {
    public static final String code = Constants.AttributeType.STRING;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public Class<?> getAttributeClass() {
        return String.class;
    }
}
