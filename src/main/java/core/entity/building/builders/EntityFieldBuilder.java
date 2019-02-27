package core.entity.building.builders;

import core.Constants;
import core.entity.building.BuildingException;
import core.entity.field.EntityField;
import core.interfaces.HasLength;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.dynamic.DynamicType.Builder;

import javax.annotation.Nullable;
import javax.persistence.Column;
import java.util.Arrays;

@SuppressWarnings("unused")
public class EntityFieldBuilder extends AbstractBuilder<EntityField> {

    @Override
    public Class<EntityField> getSuitableClass() {
        return EntityField.class;
    }

    /**
     * Building entity field from entity blank
     *
     * @param classBuilder blank for entity class
     * @return Builder with field
     * @throws BuildingException if building is failed
     */
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
                .defineField(entityField.getCode(), entityField.getFieldClass())
                .annotateField(annotationDescription.build());
    }

    /**
     * Set length for column in entity table
     *
     * @param annotationDescription annotationDescription
     * @param entityField           field for column
     */
    private AnnotationDescription.Builder setLength(AnnotationDescription.Builder annotationDescription, EntityField entityField) {
        if (!Arrays.asList(entityField.getClass().getInterfaces()).contains(HasLength.class)) {
            return annotationDescription;
        }
        int length = ((HasLength) entityField).getLength();
        if (length == Constants.HasLength.DEFAUIT) {
            return annotationDescription;
        }
        return annotationDescription.define(Constants.HasLength.LENGTH, length);
    }
}
