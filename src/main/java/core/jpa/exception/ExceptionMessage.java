package core.jpa.exception;

public interface ExceptionMessage {

    /**
     * Get code for exception message
     */
    String getMessageCode();

    /**
     * Get default message for exception
     */
    String getDefaultMessage();
}

