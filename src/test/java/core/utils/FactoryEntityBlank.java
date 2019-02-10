package core.utils;

import core.jpa.entity.EntityBlank;

public class FactoryEntityBlank {

    public static EntityBlank create(){
        EntityBlank entityBlank = new EntityBlank();
        entityBlank.setCode(RandomUtils.getCode());
        entityBlank.setTitle(RandomUtils.getTitle());
        return entityBlank;
    }
}
