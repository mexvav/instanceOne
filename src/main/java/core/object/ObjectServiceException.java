package core.object;

import core.exception.ExceptionMessage;
import core.exception.LocalizedException;

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
        SEARCHING_OPERATON_NOT_REGISTERED("searching_operation_not_registered", "Searching operation not registered");

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
