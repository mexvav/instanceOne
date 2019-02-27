package core.object.resolving;

import core.exception.ExceptionMessage;
import core.exception.LocalizedException;

/**
 * Exception for resolving service
 */
public class ResolvingException extends LocalizedException {

    /**
     * {@inheritDoc}
     */
    public ResolvingException(ResolvingException.ExceptionCauses message, String... params) {
        super(message, params);
    }

    /**
     * {@inheritDoc}
     */
    public ResolvingException(ResolvingException.ExceptionCauses message, Throwable cases, String... params) {
        super(message, cases, params);
    }

    /**
     * {@inheritDoc}
     */
    public ResolvingException(Throwable cases) {
        super(cases);
    }

    public enum ExceptionCauses implements ExceptionMessage {
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
