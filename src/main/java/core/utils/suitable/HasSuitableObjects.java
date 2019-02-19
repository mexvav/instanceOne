package core.utils.suitable;

/**
 * Contains collection {@link SuitableObject}
 */
public interface HasSuitableObjects<R extends SuitableObject> {

    /**
     * Initialize suitable object
     *
     * @param suitableObject initializing object
     */
    void initSuitableObject(R suitableObject);

    /**
     * Get {@link SuitableObject} for object
     *
     * @param object for
     */
    R getSuitableObject(final Object object);
}
