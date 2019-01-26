package core.jpa.attribute.builder;

import core.jpa.RuntimeHelper;
import core.jpa.attribute.Attribute;
import core.jpa.attribute.type.AttributeType;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.NotFoundException;

public abstract class AbstractAttributeBuilder implements AttributeBuilder {

    protected void validationAttribute(Attribute attribute) {
        AttributeType attributeType = attribute.getType();
        if (!attributeType.getTypeCode().equals(getTypeCode())) {
            throw new RuntimeException("Builder don't support this attribute type: " + attributeType.getTypeCode());
        }
    }

    @Override
    public void build(CtClass ctClass, Attribute attribute) {
        validationAttribute(attribute);
        try {
            RuntimeHelper.addSimpleField(attribute.getCode(), ctClass,
                    attribute.getType().getAttributeClass(), attribute.isRequired(), attribute.isUnique());
        } catch (NotFoundException | CannotCompileException e) {
            e.printStackTrace();
        }
    }

}
