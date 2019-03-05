package core.utils;

import com.google.common.collect.Maps;
import org.reflections.Reflections;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

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
     * Make something action on sub classes
     *
     * @param packagePath package for scan
     * @param superClass
     * @param consumer
     */
    public static <C> void actionWithSubTypes(@NotNull String packagePath, Class<C> superClass, Consumer<Class<? extends C>> consumer) {
        Reflections reflections = new Reflections(Objects.requireNonNull(packagePath));
        Set<Class<? extends C>> classes = reflections.getSubTypesOf(superClass);
        classes.forEach(actionClass -> {
            if (!Modifier.isAbstract(actionClass.getModifiers()) && !actionClass.isInterface()) {
                consumer.accept(actionClass);
            }
        });
    }
}
