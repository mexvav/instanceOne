package core.jpa.entity;

/**
 * Exception for entity builder
 */
public class EntityServiceException extends RuntimeException {
    enum ExceptionCauses {
        ENTITY_IS_NOT_EXIST("Entity by code '%s' is not exist"),
        SERVICE_RELOAD_ERROR("Entity service can't reload, cause: %s"),
        SESSION_FACTORY_IS_NOT_RELOADABLE("SessionFactory is not reloadable");

        public String getCause() {
            return cause;
        }

        private String cause;

        ExceptionCauses(String cause) {
            this.cause = cause;
        }
    }

    EntityServiceException(String message) {
        super(message);
    }

    EntityServiceException(ExceptionCauses cause) {
        super(cause.getCause());
    }

    EntityServiceException(ExceptionCauses cause, String... args) {
        super(String.format(cause.getCause(), (Object[]) args));
    }
}
