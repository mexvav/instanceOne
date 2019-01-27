package core.jpa.attribute.type;

import java.util.Date;

public class AttributeDateType extends AttributeSimpleType {
    public static final String code = "date";

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public Class<?> getAttributeClass() {
        return Date.class;
    }
}
