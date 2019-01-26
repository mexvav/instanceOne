package core.jpa.attribute.builder;

import core.jpa.attribute.Attribute;
import javassist.CtClass;

public interface AttributeBuilder {
    
    String getTypeCode();

    void build(CtClass ctClass, Attribute attribute);
}
