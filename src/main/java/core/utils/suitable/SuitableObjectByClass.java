package core.utils.suitable;

public interface SuitableObjectByClass<C, S extends HasSuitableObjectsByClass<? extends SuitableObjectByClass>> extends SuitableObject<C, S> {

    Class<C> getSuitableClass();

    void init(S hasSuitableClassObjects);
}
