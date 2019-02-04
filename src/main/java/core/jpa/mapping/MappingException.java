package core.jpa.mapping;

/**
 * Exception for entity generator
 */
public class MappingException extends RuntimeException {
    enum ExceptionCauses {
        MAPPER_NOT_FOUND("Suitable mapper not found: %s -> %s");

        private String cause;

        ExceptionCauses(String cause) {
            this.cause = cause;
        }

        public String getCause() {
            return cause;
        }
    }

    public MappingException(String message) {
        super(message);
    }

    MappingException(ExceptionCauses cause) {
        super(cause.getCause());
    }

    MappingException(ExceptionCauses cause, String... args) {
        super(String.format(cause.getCause(), args));
    }
}
