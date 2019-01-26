package core.jpa.attribute.builder;

import core.jpa.attribute.type.AttributeStringType;

public class AttributeStringBuilder extends AbstractAttributeBuilder {

    @Override
    public String getTypeCode() {
        return AttributeStringType.code;
    }
}
