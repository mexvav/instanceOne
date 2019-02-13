package core.jpa.entity.building.builders;

import core.jpa.entity.EntityBlank;
import core.jpa.entity.attribute.Attribute;
import core.jpa.entity.building.BuildingException;
import core.jpa.entity.entities.AbstractEntity;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("unused")
public class BuilderEntity extends BuilderAbstract<EntityBlank> {

    private static final String TABLE_NAME = "name";
    private static final String ENTITY_CODE_METHOD = "getEntityCode";

    @Override
    public Class<EntityBlank> getSuitableClass() {
        return EntityBlank.class;
    }

    /**
     * Building entity from entity blank. Param ctClass is ignored
     *
     * @param entityBlank entity blank
     * @return Class with annotation Entity and Table
     * @throws BuildingException if building is failed
     */
    @Override
    public Builder build(@Nullable final Builder classBuilder, EntityBlank entityBlank) {
        validateEntityBlank(entityBlank);

        Builder builder = new ByteBuddy()
                .subclass(AbstractEntity.class)
                .name(AbstractEntity.class.getPackage().getName() + "." + entityBlank.getCode().toLowerCase())
                .annotateType(AnnotationDescription.Builder
                        .ofType(Entity.class)
                        .build()
                )
                .annotateType(AnnotationDescription.Builder
                        .ofType(Table.class)
                        .define(TABLE_NAME, entityBlank.getCode())
                        .build()
                )
                .method(ElementMatchers.nameContains(ENTITY_CODE_METHOD))
                .intercept(FixedValue.value(entityBlank.getCode()));

        for (Attribute attribute : entityBlank.getAttributes()) {
            builder = buildingService.build(builder, attribute);
        }
        return builder;
    }

    /**
     * Validation EntityBlank
     *
     * @param entityBlank entity blank
     * @throws BuildingException if validation is failed
     */
    private void validateEntityBlank(EntityBlank entityBlank) {
        if (entityBlank.getCode().isEmpty()) {
            throw new BuildingException(BuildingException.ExceptionCauses.CODE_IS_EMPTY);
        }
    }
}
