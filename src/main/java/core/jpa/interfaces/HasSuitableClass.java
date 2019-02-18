package core.jpa.interfaces;

public interface HasSuitableClass<C, S extends HasSuitableClassObjects<? extends HasSuitableClass>> {

    Class<C> getSuitableClass();

    void init(S hasSuitableClassObjects);
}
