package core.jpa.entity;

import core.jpa.exception.ExceptionMessage;
import core.jpa.exception.LocalizedException;

/**
 * Exception for entity builder
 */
public class EntityServiceException extends LocalizedException {

    /**
     * {@inheritDoc}
     */
    public EntityServiceException(EntityServiceException.ExceptionCauses message, String... params) {
        super(message, params);
    }

    /**
     * {@inheritDoc}
     */
    public EntityServiceException(EntityServiceException.ExceptionCauses message, Throwable cases, String... params) {
        super(message, cases, params);
    }

    /**
     * {@inheritDoc}
     */
    public EntityServiceException(Throwable cases) {
        super(cases);
    }

    public enum ExceptionCauses implements ExceptionMessage {
        ENTITY_IS_NOT_EXIST("entity_is_not_exist", "Entity is not exist"),
        ENTITY_FIELD_TYPE_NOT_FOUND("entity_field_type_not_found", "Entity not found"),
        SERVICE_RELOAD_ERROR("service_reload_error", "Entity service can't reload"),
        SESSION_FACTORY_IS_NOT_RELOADABLE("session_factory_is_not_reloadable", "SessionFactory is not reloadable"),
        ENTITY_SERVICE_NOT_EXIST("entity_service_not_exist", "There is not exist EntityService component");;

        private String messageCode;
        private String defaultMessage;

        ExceptionCauses(String messageCode, String defaultMessage) {
            this.messageCode = messageCode;
            this.defaultMessage = defaultMessage;
        }

        @Override
        public String getMessageCode() {
            return messageCode;
        }

        @Override
        public String getDefaultMessage() {
            return defaultMessage;
        }
    }
}
