package core.utils.register;

public interface RegisteringServiceByClass<R extends RegisteredObjectWithClass> extends RegisteringService<R> {

    /**
     * Get registered object
     *
     * @param object the id for getting object
     */
    R get(final Object object);
}
