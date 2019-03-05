package core.utils.register;

public interface RegisteringServiceByCode<R extends RegisteredObjectWithCode> extends RegisteringService<R> {

    /**
     * Get registered object by code
     *
     * @param code the code for getting object
     */
    R get(final String code);
}
