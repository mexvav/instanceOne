package core.utils.register;

public interface RegisteringService<R extends RegisteredObject> {

    /**
     * Register object in service
     *
     * @param registeredObject object for registration
     */
    void register(R registeredObject);
}
