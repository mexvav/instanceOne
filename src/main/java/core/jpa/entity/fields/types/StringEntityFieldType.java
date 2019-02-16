package core.jpa.entity.fields.types;

import core.jpa.Constants;

@SuppressWarnings("unused")
public class StringEntityFieldType extends SimpleEntityFieldType {

    public static final String code = Constants.AttributeType.STRING;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public Class<?> getFieldClass() {
        return String.class;
    }

}
