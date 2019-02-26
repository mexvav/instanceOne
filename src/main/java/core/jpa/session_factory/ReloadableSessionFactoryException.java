package core.jpa.session_factory;

import core.jpa.exception.ExceptionMessage;
import core.jpa.exception.LocalizedException;

/**
 * Exception for mapping service
 */
public class ReloadableSessionFactoryException extends LocalizedException {

    /**
     * {@inheritDoc}
     */
    public ReloadableSessionFactoryException(ExceptionCauses message, String... params) {
        super(message, params);
    }

    /**
     * {@inheritDoc}
     */
    public ReloadableSessionFactoryException(ExceptionCauses message, Throwable cases, String... params) {
        super(message, cases, params);
    }

    /**
     * {@inheritDoc}
     */
    public ReloadableSessionFactoryException(Throwable cases) {
        super(cases);
    }

    public enum ExceptionCauses implements ExceptionMessage {
        MAPPER_NOT_FOUND("mapper_not_found", "Suitable mapper not found");

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
