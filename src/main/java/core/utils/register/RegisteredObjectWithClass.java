package core.utils.register;

public interface RegisteredObjectWithClass<C, S extends RegisteringServiceByClass<? extends RegisteredObjectWithClass>> extends RegisteredObject<C, S> {

    /**
     * Get suitable class for this {@link RegisteredObjectWithClass}
     */
    Class<C> getSuitableClass();
}
