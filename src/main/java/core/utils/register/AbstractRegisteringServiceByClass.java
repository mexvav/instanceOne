package core.utils.register;

import com.google.common.collect.Sets;
import core.utils.ClassUtils;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractRegisteringServiceByClass<R extends RegisteredObjectWithClass> extends AbstractRegisteringService<R> implements RegisteringServiceByClass<R> {

    private Set<R> registeredObjects;

    /**
     * {@inheritDoc}
     */
    @Override
    public void register(R object) {
        getRegisteredObjects().add(object);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public R get(final Object object) {
        Set<R> suitableClassObjects = getRegisteredObjects().stream()
                .filter(suitableClassObject ->
                        suitableClassObject.getSuitableClass().isAssignableFrom(object.getClass()))
                .collect(Collectors.toSet());
        if (suitableClassObjects.isEmpty()) {
            throw new RegisterException(
                    RegisterException.ExceptionCauses.SUITABLE_OBJECT_CLASS_NOT_FOUND,
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

    /**
     * Get all registered objects
     */
    protected Set<R> getRegisteredObjects() {
        if (null == registeredObjects) {
            registeredObjects = Sets.newHashSet();
        }
        return registeredObjects;
    }
}
