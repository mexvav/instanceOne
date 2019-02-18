package core.jpa.interfaces;

public interface HasSuitableClassObjects<R extends HasSuitableClass> {

    void initSuitableClassObject(R hasSuitableClassObject);

    R getSuitableClassObject(final Object object);
}
