package core.jpa.entity.building.builders;

import core.jpa.entity.attribute.Attribute;
import net.bytebuddy.dynamic.DynamicType.Builder;

import javax.annotation.Nullable;

@SuppressWarnings("unused")
public class BuilderAttribute extends BuilderAbstract<Attribute> {

    @Override
    public Class<Attribute> getSuitableClass() {
        return Attribute.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Builder build(@Nullable Builder classBuilder, Attribute buildObject) {
        return buildingService.getBuilder(buildObject.getType()).build(classBuilder, buildObject);
    }
}
