package core.entity.building;

import core.exception.ExceptionMessage;
import core.exception.LocalizedException;

/**
 * Exception for building Entity
 */
public class BuildingException extends LocalizedException {

    /**
     * {@inheritDoc}
     */
    public BuildingException(BuildingException.ExceptionCauses message, String... params) {
        super(message, params);
    }

    /**
     * {@inheritDoc}
     */
    public BuildingException(BuildingException.ExceptionCauses message, Throwable cases, String... params) {
        super(message, cases, params);
    }

    /**
     * {@inheritDoc}
     */
    public BuildingException(Throwable cases) {
        super(cases);
    }

    public enum ExceptionCauses implements ExceptionMessage {
        CODE_IS_EMPTY("code_is_empty", "Code is empty"),
        SUITABLE_BUILDER_NOT_FOUND("suitable_builder_not_found", "Suitable builder not found");

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
