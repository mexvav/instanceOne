package core.jpa.entity.building.builders;

import core.jpa.entity.attribute.Attribute;
import javassist.CtClass;

import javax.annotation.Nullable;

public class BuilderAttribute extends BuilderAbstract<Attribute> {

    @Override
    public Class<Attribute> getSuitableClass() {
        return Attribute.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public CtClass build(@Nullable CtClass ctClass, Attribute buildObject) {
        return buildingService.getBuilder(buildObject.getType()).build(ctClass, buildObject);
    }
}
