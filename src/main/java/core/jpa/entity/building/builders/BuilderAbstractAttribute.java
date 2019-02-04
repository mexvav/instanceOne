package core.jpa.entity.building.builders;

import core.jpa.entity.attribute.Attribute;
import core.jpa.entity.attribute.type.AttributeType;
import core.jpa.entity.building.BuilderService;

abstract class BuilderAbstractAttribute<T extends AttributeType> implements BuilderAttribute<T> {

    /**
     * Validate attribute builder
     *
     * @throws core.jpa.entity.building.BuilderService.BuilderException if builder is not suitable
     */
    void validationAttribute(Attribute attribute) {
        AttributeType attributeType = attribute.getType();
        if (!getTypeClass().isAssignableFrom(attributeType.getClass())) {
            throw new BuilderService.BuilderException(BuilderService.BuilderException.ExceptionCauses.NOT_SUITABLE_ATTRIBUTE_BUILDER, attributeType.getCode());
        }
    }
}
