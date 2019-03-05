package core.utils.register;

import com.google.common.collect.Maps;

import java.util.Map;

public abstract class AbstractRegisteringServiceByCode<R extends RegisteredObjectWithCode> extends AbstractRegisteringService<R> implements RegisteringServiceByCode<R> {

    private Map<String, R> registeredObjects;

    /**
     * {@inheritDoc}
     */
    @Override
    public void register(R object) {
        getRegisteredObjects().put(object.getCode(), object);
    }

    /**
     * Get SuitableClassObject for object by object class hierarchy
     *
     * @param code object
     * @return SuitableClassObject
     */
    @Override
    public R get(final String code) {
        if (getRegisteredObjects().containsKey(code)) {
            return getRegisteredObjects().get(code);
        }
        throw new RegisterException(
                RegisterException.ExceptionCauses.SUITABLE_OBJECT_CODE_NOT_FOUND,
                code.getClass().getName());
    }

    /**
     * Get all registered objects
     */
    private Map<String, R> getRegisteredObjects() {
        if (null == registeredObjects) {
            registeredObjects = Maps.newHashMap();
        }
        return registeredObjects;
    }
}
