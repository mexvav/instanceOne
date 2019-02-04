package core.jpa.entity.attribute.type;

import core.jpa.Constants;

import java.util.Date;

public class AttributeDateType extends AttributeSimpleType {
    public static final String code = Constants.AttributeType.DATE;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public Class<?> getAttributeClass() {
        return Date.class;
    }
}
