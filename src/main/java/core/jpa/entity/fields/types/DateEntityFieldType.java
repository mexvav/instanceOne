package core.jpa.entity.fields.types;

import core.jpa.Constants;

import java.util.Date;

@SuppressWarnings("unused")
public class DateEntityFieldType extends SimpleEntityFieldType {

    public static final String code = Constants.AttributeType.DATE;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public Class<?> getFieldClass() {
        return Date.class;
    }
}
