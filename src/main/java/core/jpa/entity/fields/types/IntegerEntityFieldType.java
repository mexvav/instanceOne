package core.jpa.entity.fields.types;

import core.jpa.Constants;

@SuppressWarnings("unused")
public class IntegerEntityFieldType extends SimpleEntityFieldType {

    public static final String code = Constants.AttributeType.INTEGER;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public Class<?> getFieldClass() {
        return Integer.class;
    }
}
