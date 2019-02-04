package core.jpa.entity.building.builders;

import core.jpa.entity.attribute.Attribute;
import core.jpa.entity.attribute.type.AttributeType;
import javassist.CtClass;

public interface BuilderAttribute<T extends AttributeType> {

    /**
     * Get type class
     */
    Class<T> getTypeClass();

    /**
     * Add attribute field to entity class blank({@link CtClass})
     *
     * @param ctClass entity class blank
     * @param attribute attribute blank
     * @throws core.jpa.entity.building.BuilderService.BuilderException if build fail
     */
    void build(CtClass ctClass, Attribute attribute);
}
