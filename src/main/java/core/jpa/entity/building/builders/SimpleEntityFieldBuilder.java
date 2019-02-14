package core.jpa.entity.building.builders;

import core.jpa.entity.fields.EntityField;
import core.jpa.entity.fields.types.SimpleEntityFieldType;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.dynamic.DynamicType.Builder;

import javax.annotation.Nullable;
import javax.persistence.Column;

@SuppressWarnings("unused")
public class SimpleEntityFieldBuilder extends AbstractBuilder<EntityField> {

    @Override
    public Class<SimpleEntityFieldType> getSuitableClass() {
        return SimpleEntityFieldType.class;
    }

    @Override
    public Builder build(@Nullable final Builder classBuilder, EntityField entityField) {
        if (null == classBuilder) {
            return null;
        }

        return classBuilder
                .defineField(entityField.getCode(), entityField.getType().getFieldClass())
                .annotateField(AnnotationDescription.Builder.ofType(Column.class).build());
    }
}
