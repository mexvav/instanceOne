package core.utils.register;

/**
 * Exception for suitable classes
 */
public class RegisterException extends RuntimeException {

    public RegisterException(String message) {
        super(message);
    }

    public RegisterException(ExceptionCauses cause) {
        super(cause.getCause());
    }

    public RegisterException(ExceptionCauses cause, String... args) {
        super(String.format(cause.getCause(), (Object[]) args));
    }

    public enum ExceptionCauses {
        SUITABLE_OBJECT_CLASS_NOT_FOUND("Suitable object for class: '%s' not found"),
        SUITABLE_OBJECT_CODE_NOT_FOUND("Suitable object for code: '%s' not found");

        private String cause;

        ExceptionCauses(String cause) {
            this.cause = cause;
        }

        public String getCause() {
            return cause;
        }
    }
}
