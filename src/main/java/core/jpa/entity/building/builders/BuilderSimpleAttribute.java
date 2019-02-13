package core.jpa.entity.building.builders;

import core.jpa.entity.attribute.Attribute;
import core.jpa.entity.attribute.type.AttributeSimpleType;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.dynamic.DynamicType.Builder;

import javax.annotation.Nullable;
import javax.persistence.Column;

@SuppressWarnings("unused")
public class BuilderSimpleAttribute extends BuilderAbstract<Attribute> {

    @Override
    public Class<AttributeSimpleType> getSuitableClass() {
        return AttributeSimpleType.class;
    }

    @Override
    public Builder build(@Nullable final Builder classBuilder, Attribute attribute) {
        if (null == classBuilder) {
            return null;
        }

        return classBuilder
                .defineField(attribute.getCode(), attribute.getType().getAttributeClass())
                .annotateField(AnnotationDescription.Builder.ofType(Column.class).build());
    }
}