package core.utils.suitable;

public interface SuitableObjectByClass<C, S extends HasSuitableObjectsByClass<? extends SuitableObjectByClass>> extends SuitableObject<C, S> {

    /**
     * Get suitable class for this {@link SuitableObject}
     */
    Class<C> getSuitableClass();

    /**
     * Initialize current object in {@link HasSuitableObjectsByClass}
     * @param hasSuitableClassObjects {@link HasSuitableObjectsByClass}
     */
    void init(S hasSuitableClassObjects);
}
