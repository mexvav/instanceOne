package core.jpa.entity.building;

/**
 * Exception for building Entity
 */
public class BuildingException extends RuntimeException {
    public BuildingException(String message) {
        super(message);
    }

    BuildingException(ExceptionCauses cause) {
        super(cause.getCause());
    }

    public BuildingException(ExceptionCauses cause, String... args) {
        super(String.format(cause.getCause(), (Object[]) args));
    }

    public enum ExceptionCauses {
        CODE_IS_EMPTY("Code is empty, use setCode(code)"),
        SUITABLE_BUILDER_NOT_FOUND("Suitable attribute for class: %s not found");

        private String cause;

        ExceptionCauses(String cause) {
            this.cause = cause;
        }

        public String getCause() {
            return cause;
        }
    }
}