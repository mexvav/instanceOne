package core.jpa.object;

/**
 * Exception for object service
 */
public class ObjectServiceException extends RuntimeException {

    public ObjectServiceException(String message) {
        super(message);
    }

    public ObjectServiceException(ExceptionCauses cause) {
        super(cause.getCause());
    }

    public ObjectServiceException(ExceptionCauses cause, String... args) {
        super(String.format(cause.getCause(), (Object[]) args));
    }

    public enum ExceptionCauses {
        ATTRIBUTE_IS_NOT_EXIST("Entity '%s' is not contains attribute '%s'"),
        RESOLVER_NOT_FOUND("Not found suitable resolver for type '%s'"),
        RESOLVING_IS_FAILED("Resolving value for type '%s' is failed with '%s'"),
        DATE_RESOLVING_IS_FAILED("Resolving value from '%s' for Date type is failed. Expected date format '%s'."),
        ;

        private String cause;

        ExceptionCauses(String cause) {
            this.cause = cause;
        }

        public String getCause() {
            return cause;
        }
    }
}
