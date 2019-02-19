package core.utils;

import org.reflections.Reflections;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Modifier;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

public class ReflectionUtils {

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
