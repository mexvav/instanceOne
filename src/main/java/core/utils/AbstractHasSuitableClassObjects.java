package core.utils;

import com.google.common.collect.Sets;
import core.jpa.entity.building.BuildingException;
import core.jpa.entity.building.builders.Builder;
import core.jpa.interfaces.HasSuitableClass;
import core.jpa.interfaces.HasSuitableClassObjects;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public abstract class AbstractHasSuitableClassObjects<R extends HasSuitableClass> implements HasSuitableClassObjects<R> {

    private Set<R> suitableClassObjects;

    public void initSuitableClassObject(R hasSuitableClassObject) {
        getSuitableClassObjects().add(hasSuitableClassObject);
    }

    /***/
    protected abstract String getPackage();

    protected abstract Class<R> getSuitableClass();

    protected abstract Consumer<Class<? extends R>> getSuitableClassConsumer();

    /**
     * Initialize all SuitableClassObject in package {@link #getPackage()}
     */
    protected void initializeSuitableClassObjects() {
        ReflectionUtils.actionWithSubTypes(Builder.class.getPackage().getName(),
                getSuitableClass(), getSuitableClassConsumer());
    }

    /**
     * Get all {@link HasSuitableClass}
     */
    protected Set<R> getSuitableClassObjects() {
        if (null == suitableClassObjects) {
            suitableClassObjects = Sets.newHashSet();
        }
        return suitableClassObjects;
    }

    /**
     * Get SuitableClassObject for object by object class hierarchy
     *
     * @param object object
     * @return SuitableClassObject
     */
    @SuppressWarnings("unchecked")
    public R getSuitableClassObject(final Object object) {
        Set<R> suitableClassObjects = getSuitableClassObjects().stream()
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
