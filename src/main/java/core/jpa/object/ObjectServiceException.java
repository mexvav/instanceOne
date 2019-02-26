package core.jpa.object;

import core.jpa.exception.ExceptionMessage;
import core.jpa.exception.LocalizedException;

/**
 * Exception for object service
 */
public class ObjectServiceException extends LocalizedException {

    /**
     * {@inheritDoc}
     */
    public ObjectServiceException(ObjectServiceException.ExceptionCauses message, String... params) {
        super(message, params);
    }

    /**
     * {@inheritDoc}
     */
    public ObjectServiceException(ObjectServiceException.ExceptionCauses message, Throwable cases, String... params) {
        super(message, cases, params);
    }

    /**
     * {@inheritDoc}
     */
    public ObjectServiceException(Throwable cases) {
        super(cases);
    }

    public enum ExceptionCauses implements ExceptionMessage {
        FIELD_IS_NOT_EXIST("field_is_not_exist", "Entity is not contains field"),
        RESOLVER_NOT_FOUND("resolver_not_found", "Suitable resolver is not found"),
        RESOLVING_IS_FAILED("resolving_is_failed", "Resolving value is failed"),
        DATE_RESOLVING_IS_FAILED("date_resolving_is_failed", "Resolving value for Date type is failed");

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
