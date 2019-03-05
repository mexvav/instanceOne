package core.mapping;

import core.exception.LocalizedException;
import core.exception.ExceptionMessage;

/**
 * Exception for mapping service
 */
public class MappingException extends LocalizedException {

    /**
     * {@inheritDoc}
     */
    public MappingException(ExceptionCauses message, String... params) {
        super(message, params);
    }

    /**
     * {@inheritDoc}
     */
    public MappingException(ExceptionCauses message, Throwable cases, String... params) {
        super(message, cases, params);
    }

    /**
     * {@inheritDoc}
     */
    public MappingException(Throwable cases) {
        super(cases);
    }

    public enum ExceptionCauses implements ExceptionMessage {
        MAPPER_NOT_FOUND("mapper_not_found", "Suitable mapper not found"),
        MAPPING_NULL("mapping_null", "Can not mapping null object");

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
