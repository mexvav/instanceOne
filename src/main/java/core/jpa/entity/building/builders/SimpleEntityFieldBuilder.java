package core.jpa.entity.building.builders;

import core.jpa.Constants;
import core.jpa.entity.fields.EntityField;
import core.jpa.entity.fields.types.EntityFieldType;
import core.jpa.entity.fields.types.SimpleEntityFieldType;
import core.jpa.interfaces.HasLength;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.dynamic.DynamicType.Builder;

import javax.annotation.Nullable;
import javax.persistence.Column;
import java.util.Arrays;

@SuppressWarnings("unused")
public class SimpleEntityFieldBuilder extends AbstractBuilder<EntityField> {

    @Override
    public Class<SimpleEntityFieldType> getSuitableClass() {
        return SimpleEntityFieldType.class;
    }

    @Override
    @Nullable
    public Builder build(@Nullable final Builder classBuilder, @Nullable EntityField entityField) {
        if (null == classBuilder) {
            return null;
        }
        if (null == entityField) {
            return classBuilder;
        }

        AnnotationDescription.Builder annotationDescription = AnnotationDescription.Builder.ofType(Column.class);
        annotationDescription = setLength(annotationDescription, entityField);

        return classBuilder
                .defineField(entityField.getCode(), entityField.getType().getFieldClass())
                .annotateField(annotationDescription.build());
    }

    /**
     * Set length for column in entity table
     *
     * @param annotationDescription annotationDescription
     * @param entityField field for column
     */
    private AnnotationDescription.Builder setLength(AnnotationDescription.Builder annotationDescription, EntityField entityField) {
        EntityFieldType entityFieldType = entityField.getType();

        if (!Arrays.asList(entityFieldType.getClass().getInterfaces()).contains(HasLength.class)) {
            return annotationDescription;
        }
        int length = ((HasLength) entityFieldType).getLength();
        if (length == Constants.HasLength.DEFAUIT) {
            return annotationDescription;
        }
        return annotationDescription.define(Constants.HasLength.LENGTH, length);
    }
}
