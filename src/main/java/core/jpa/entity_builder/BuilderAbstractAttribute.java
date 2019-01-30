package core.jpa.entity_builder;

import core.jpa.attribute.Attribute;
import core.jpa.attribute.type.AttributeType;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BuilderAbstractAttribute<T extends AttributeType> implements BuilderAttribute<T> {

    @Autowired
    BuilderAbstractAttribute(BuilderService builderService){
        initAttributeBuilder(builderService);
    }

    protected void validationAttribute(Attribute attribute) {
        AttributeType attributeType = attribute.getType();
        if (!getTypeClass().isAssignableFrom(attributeType.getClass())) {
            throw new RuntimeException("Builder don't support this attribute type: " + attributeType.getCode());
        }
    }
}
