package core.utils.suitable;

public interface HasSuitableObjects<R extends SuitableObject> {

    void initSuitableObject(R hasSuitableClassObject);

    R getSuitableObject(final Object object);
}
