package core.utils;

import com.google.common.collect.Maps;
import core.jpa.entity.building.BuildingException;
import core.jpa.interfaces.HasSuitableClass;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ClassUtils {

    /**
     * Get class hierarchy with rate rate 0 - source class rate 1 - interfaces source class
     *
     * @param nextClass - class for hierarchy
     * @return Map class/rate
     */
    public static Map<Class, Integer> getHierarchyClass(Class nextClass) {
        Map<Class, Integer> hierarchyClass = Maps.newHashMap();
        int rate = 0;
        while (!nextClass.equals(Object.class)) {
            hierarchyClass.put(nextClass, rate++);
            Class<?>[] interfaces = nextClass.getInterfaces();
            if (interfaces.length > 0) {
                rate++;
                for (Class interfaceTemp : interfaces) {
                    hierarchyClass.put(interfaceTemp, rate);
                }
            }
            nextClass = nextClass.getSuperclass();
        }
        return hierarchyClass;
    }

    /**
     * Get SuitableClassObject for object by object class hierarchy
     *
     * @param objects collection {@link HasSuitableClass}
     * @param object  object
     * @return SuitableClassObject
     */
    @SuppressWarnings("unused")
    public static <R extends HasSuitableClass> R getSuitableClassObject(Set<R> objects, final Object object) {
        Set<R> suitableClassObjects = objects.stream()
                .filter(suitableClassObject ->
                        suitableClassObject.getSuitableClass().isAssignableFrom(object.getClass()))
                .collect(Collectors.toSet());
        if (suitableClassObjects.isEmpty()) {
            throw new BuildingException(
                    BuildingException.ExceptionCauses.SUITABLE_BUILDER_NOT_FOUND,
                    object.getClass().getName());
        }
        if (suitableClassObjects.size() == 1) {
            return suitableClassObjects.iterator().next();
        }
        Map<Class, Integer> fromClasses = ClassUtils.getHierarchyClass(object.getClass());
        return suitableClassObjects.stream()
                .min(Comparator.comparingInt(b -> fromClasses.get(b.getSuitableClass())))
                .get();
    }
}
