package core.utils.suitable;

import com.google.common.collect.Maps;

import java.util.Map;

public abstract class AbstractHasSuitableObjectsByCode<R extends SuitableObjectByCode> extends AbstractHasSuitableObjects<R> implements HasSuitableObjectsByCode<R> {

    private Map<String, R> suitableObjects;

    public void initSuitableObject(R hasSuitableClassObject) {
        getSuitableObjects().put(hasSuitableClassObject.getCode(), hasSuitableClassObject);
    }

    /**
     * Get all {@link SuitableObjectByClass}
     */
    protected Map<String, R> getSuitableObjects() {
        if (null == suitableObjects) {
            suitableObjects = Maps.newHashMap();
        }
        return suitableObjects;
    }

    /**
     * Get SuitableClassObject for object by object class hierarchy
     *
     * @param object object
     * @return SuitableClassObject
     */
    @SuppressWarnings("unchecked")
    public R getSuitableObject(final Object object) {
        if (getSuitableObjects().containsKey(object)) {
            return getSuitableObjects().get(object);
        }
        throw new SuitableException(
                SuitableException.ExceptionCauses.SUITABLE_OBJECT_NOT_FOUND,
                object.getClass().getName());
    }
}
