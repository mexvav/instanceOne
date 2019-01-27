package core.jpa.builder;

import core.jpa.attribute.Attribute;
import core.jpa.attribute.type.AttributeType;
import javassist.CtClass;

public interface BuilderAttribute<T extends AttributeType> {
    
    Class<T> getTypeClass();

    void build(CtClass ctClass, Attribute attribute);

    default void initAttributeBuilder(BuilderService builderService){
        builderService.initAttributeBuilder(this);
    }
}
