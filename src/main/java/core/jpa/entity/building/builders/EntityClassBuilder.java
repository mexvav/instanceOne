package core.jpa.entity.building.builders;

import core.jpa.Constants;
import core.jpa.entity.EntityClass;
import core.jpa.entity.building.BuildingException;
import core.jpa.entity.entities.AbstractEntity;
import core.jpa.entity.field.EntityField;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@SuppressWarnings("unused")
public class EntityClassBuilder extends AbstractBuilder<EntityClass> {

    @Override
    public Class<EntityClass> getSuitableClass() {
        return EntityClass.class;
    }

    /**
     * Building entity from entity blank
     *
     * @param entityClass blank for entity class
     * @return Class with annotation Entity and Table
     * @throws BuildingException if building is failed
     */
    @Override
    @NotNull
    public Builder build(@Nullable final Builder classBuilder, @NotNull EntityClass entityClass) {
        validateEntityBlank(entityClass);

        Builder builder =
                new ByteBuddy()
                        .subclass(AbstractEntity.class)
                        .name(AbstractEntity.class.getPackage().getName()
                                + Constants.PERIOD
                                + StringUtils.capitalize(entityClass.getCode().toLowerCase()))
                        .annotateType(
                                AnnotationDescription.Builder.ofType(Entity.class)
                                        .define(Constants.Builder.NAME, entityClass.getCode())
                                        .build())
                        .annotateType(
                                AnnotationDescription.Builder.ofType(Table.class)
                                        .define(Constants.Builder.NAME, entityClass.getCode())
                                        .build())
                        .method(ElementMatchers.nameContains(Constants.Builder.ENTITY_CODE_METHOD))
                        .intercept(FixedValue.value(entityClass.getCode()));

        for (EntityField entityField : entityClass.getFields()) {
            builder = buildingService.build(builder, entityField);
        }
        return builder;
    }

    /**
     * Validation EntityClass
     *
     * @param entityClass blank for entity class
     * @throws BuildingException if validation is failed
     */
    private void validateEntityBlank(@NotNull EntityClass entityClass) {
        if (Objects.requireNonNull(entityClass).getCode().isEmpty()) {
            throw new BuildingException(BuildingException.ExceptionCauses.CODE_IS_EMPTY);
        }
    }
}
