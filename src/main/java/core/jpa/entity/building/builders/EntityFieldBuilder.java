package core.jpa.entity.building.builders;

import core.jpa.entity.fields.EntityField;
import net.bytebuddy.dynamic.DynamicType.Builder;

import javax.annotation.Nullable;

@SuppressWarnings("unused")
public class EntityFieldBuilder extends AbstractBuilder<EntityField> {

    @Override
    public Class<EntityField> getSuitableClass() {
        return EntityField.class;
    }

    @Override
    @Nullable
    @SuppressWarnings("unchecked")
    public Builder build(@Nullable Builder classBuilder, @Nullable EntityField buildObject) {
        if (null == classBuilder) {
            return null;
        }
        if (null == buildObject) {
            return classBuilder;
        }
        return buildingService.getBuilder(buildObject.getType()).build(classBuilder, buildObject);
    }
}
