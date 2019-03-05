package core.entity.building.builders;

import core.Constants;
import core.entity.building.BuildingException;
import core.entity.building.BuildingService;
import core.entity.field.EntityField;
import core.entity.field.fields.LinkEntityField;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.dynamic.DynamicType.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@SuppressWarnings("unused")
@Component
public class LinkEntityFieldBuilder extends AbstractBuilder<LinkEntityField> {

    @Autowired
    LinkEntityFieldBuilder(BuildingService service) {
        super(service, LinkEntityField.class);
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
    public Builder build(@Nullable final Builder classBuilder, @Nullable LinkEntityField entityField) {
        if (null == classBuilder) {
            return null;
        }
        if (null == entityField) {
            return classBuilder;
        }
        AnnotationDescription.Builder annotationOneToOne = AnnotationDescription.Builder.ofType(OneToOne.class);
        AnnotationDescription.Builder annotationJoinColumn = AnnotationDescription.Builder.ofType(JoinColumn.class);
        annotationJoinColumn.define(Constants.Builder.NAME, entityField.getCode());

        return classBuilder
                .defineField(entityField.getCode(), entityField.getFieldClass())
                .annotateField(annotationOneToOne.build(), annotationJoinColumn.build());
    }
}
