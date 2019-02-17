package core.utils;

import core.jpa.entity.EntityService;

import java.util.Objects;

public class CleanUtils {
    private static EntityService entityService;

    public static void initCleaner(EntityService entityService){
        CleanUtils.entityService = entityService;
    }

    public static void hardClean(){
        Objects.requireNonNull(entityService, "Cleaner not initialize").hardClean();
    }
}
