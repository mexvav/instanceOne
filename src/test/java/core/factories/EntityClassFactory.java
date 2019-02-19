package core.factories;

import core.jpa.entity.EntityClass;
import core.utils.RandomUtils;

public class EntityClassFactory {

    public static EntityClass create() {
        EntityClass entityClass = new EntityClass();
        entityClass.setCode(RandomUtils.getCode());
        return entityClass;
    }
}
