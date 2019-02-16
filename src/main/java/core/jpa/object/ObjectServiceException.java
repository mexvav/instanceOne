package core.jpa.object;

/**
 * Exception for object service
 */
public class ObjectServiceException extends RuntimeException {

    ObjectServiceException(String message) {
        super(message);
    }

    ObjectServiceException(ExceptionCauses cause) {
        super(cause.getCause());
    }

    ObjectServiceException(ExceptionCauses cause, String... args) {
        super(String.format(cause.getCause(), (Object[]) args));
    }

    enum ExceptionCauses {
        ATTRIBUTE_IS_NOT_EXIST("Entity '%s' is not contains attribute '%s'");

        private String cause;

        ExceptionCauses(String cause) {
            this.cause = cause;
        }

        public String getCause() {
            return cause;
        }
    }
}
