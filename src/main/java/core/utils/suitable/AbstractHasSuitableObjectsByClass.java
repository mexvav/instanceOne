package core.utils.suitable;

import com.google.common.collect.Sets;
import core.utils.ClassUtils;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractHasSuitableObjectsByClass<R extends SuitableObjectByClass> extends AbstractHasSuitableObjects<R> implements HasSuitableObjectsByClass<R> {

    private Set<R> suitableClassObjects;

    /**
     * {@inheritDoc}
     */
    public void initSuitableObject(R hasSuitableClassObject) {
        getSuitableClassObjects().add(hasSuitableClassObject);
    }

    /**
     * Get all {@link SuitableObjectByClass}
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
    public R getSuitableObject(final Object object) {
        Set<R> suitableClassObjects = getSuitableClassObjects().stream()
                .filter(suitableClassObject ->
                        suitableClassObject.getSuitableClass().isAssignableFrom(object.getClass()))
                .collect(Collectors.toSet());
        if (suitableClassObjects.isEmpty()) {
            throw new SuitableException(
                    SuitableException.ExceptionCauses.SUITABLE_OBJECT_CLASS_NOT_FOUND,
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
