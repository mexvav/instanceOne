package core.jpa.attribute;

import core.jpa.attribute.type.AttributeStringType;

public class AttributeFactory {

    public static Attribute createStringAttribute(String code) {
        Attribute attribute = new Attribute(code);
        attribute.setType(new AttributeStringType());
        return attribute;
    }
}
