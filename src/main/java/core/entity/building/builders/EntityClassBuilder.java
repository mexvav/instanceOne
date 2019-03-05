package core.entity.building.builders;

import core.Constants;
import core.entity.EntityClass;
import core.entity.building.BuildingException;
import core.entity.building.BuildingService;
import core.entity.entities.AbstractEntity;
import core.entity.field.EntityField;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@SuppressWarnings("unused")
@Component
public class EntityClassBuilder extends AbstractBuilder<EntityClass> {

    @Autowired
    EntityClassBuilder(BuildingService service) {
        super(service, EntityClass.class);
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
            builder = getBuildingService().build(builder, entityField);
        }
        return builder;
    }

    /**
     * ValidationService EntityClass
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
