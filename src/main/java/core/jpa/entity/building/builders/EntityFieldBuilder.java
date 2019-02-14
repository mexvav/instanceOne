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
    @SuppressWarnings("unchecked")
    public Builder build(@Nullable Builder classBuilder, EntityField buildObject) {
        return buildingService.getBuilder(buildObject.getType()).build(classBuilder, buildObject);
    }
}
