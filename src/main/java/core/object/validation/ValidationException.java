package core.object.validation;

import core.exception.ExceptionMessage;
import core.exception.LocalizedException;

/**
 * Exception for validation service
 */
public class ValidationException extends LocalizedException {

    /**
     * {@inheritDoc}
     */
    public ValidationException(ValidationException.ExceptionCauses message, String... params) {
        super(message, params);
    }

    /**
     * {@inheritDoc}
     */
    public ValidationException(ValidationException.ExceptionCauses message, Throwable cases, String... params) {
        super(message, cases, params);
    }

    /**
     * {@inheritDoc}
     */
    public ValidationException(Throwable cases) {
        super(cases);
    }

    public enum ExceptionCauses implements ExceptionMessage {
        VALUE_IS_REQUIRED("value_is_required", "Value is required"),
        VALUE_IS_TOO_LONG("value_is_too_long", "Value is too long");

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
