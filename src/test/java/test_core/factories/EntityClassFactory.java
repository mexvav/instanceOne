package test_core.factories;

import core.entity.EntityClass;
import test_core.utils.RandomUtils;

public class EntityClassFactory {

    public static EntityClass create() {
        EntityClass entityClass = new EntityClass();
        entityClass.setCode(RandomUtils.getCode());
        return entityClass;
    }
}
