package core.jpa;

import core.jpa.entity.building.BuildingService;
import javassist.*;

import javax.annotation.Nullable;


public class RuntimeHelper {

    static private ClassPool classPool;

    public static ClassPool getClassPool() {
        if (null == classPool) {
            classPool = ClassPool.getDefault();
            classPool.appendClassPath(new LoaderClassPath(BuildingService.class.getClassLoader()));
        }
        return classPool;
    }

    @Nullable
    public static CtClass getCtClass(String className) {
        try {
            return getClassPool().get(className);
        } catch (NotFoundException e) {
            return null;
        }
    }
}
