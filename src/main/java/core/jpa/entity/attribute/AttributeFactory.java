package core.jpa.entity.attribute;

import core.jpa.entity.attribute.type.AttributeStringType;

public class AttributeFactory {

    public static Attribute createStringAttribute(String code) {
        Attribute attribute = new Attribute(code);
        attribute.setType(new AttributeStringType());
        return attribute;
    }
}
