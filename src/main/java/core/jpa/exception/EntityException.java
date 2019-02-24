package core.jpa.exception;

import com.google.common.collect.Maps;
import org.springframework.context.i18n.LocaleContextHolder;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Exception for Runtime Entity
 */
public class EntityException extends RuntimeException {

    /**
     * Name of exception property file
     */
    private static final String EXCEPTION_MESSAGES_PROPERTIES = "exception_message";

    /**
     * Exception messages in different locales
     */
    private static Map<String, ResourceBundle> errorResourceBundles;

    /**
     * Constructs a new runtime exception with the specified detail message and cause
     * in current locale
     *
     * @param message param of localized message
     * @param cause   the cause
     * @param params  params for format exception message
     */
    public EntityException(ExceptionMessage message, Throwable cause, String... params) {
        this(message, LocaleContextHolder.getLocale().getLanguage(), cause, params);
    }

    /**
     * Constructs a new runtime exception with the specified detail message
     * in current locale
     *
     * @param message param of localized message
     * @param params  params for format exception message
     */
    public EntityException(ExceptionMessage message, String... params) {
        this(message, LocaleContextHolder.getLocale().getLanguage(), params);
    }

    /**
     * Constructs a new runtime exception with the specified detail message and cause
     * in specified language
     *
     * @param message  the param of localized message
     * @param language the specified language
     * @param cause    the cause of exception
     * @param params   params for format exception message
     */
    public EntityException(ExceptionMessage message, String language, Throwable cause, String... params) {
        this(getExceptionMessage(message, language, params), cause);
    }

    /**
     * Constructs a new runtime exception with the specified detail message
     * in specified language
     *
     * @param message  the param of localized message
     * @param language the specified language
     * @param params   params for format exception message
     */
    public EntityException(ExceptionMessage message, String language, String... params) {
        this(getExceptionMessage(message, language, params));
    }

    /**
     * Constructs a new runtime exception with the specified detail message and cause
     *
     * @param message the specified detail
     * @param cause   the cause of exception
     * @param params  params for format exception message
     */
    public EntityException(String message, Throwable cause, String... params) {
        super(getExceptionMessage(message, params), cause);
    }

    /**
     * Constructs a new runtime exception with the specified detail message
     *
     * @param message the specified detail
     * @param params  params for format exception message
     */
    public EntityException(String message, String... params) {
        super(getExceptionMessage(message, params));
    }

    /**
     * Constructs a new runtime exception with the specified cause
     *
     * @param cause the cause of exception
     */
    public EntityException(Throwable cause) {
        super(cause);
    }

    /**
     * Get exception message
     *
     * @param message  {@link ExceptionMessage}
     * @param language language for exception
     * @param params   params for format exception message
     */
    private static String getExceptionMessage(ExceptionMessage message, String language, String... params) {
        ResourceBundle resourceBundle = getErrorResourceBundle(language);
        if (resourceBundle.containsKey(message.getMessageCode())) {
            return getExceptionMessage(resourceBundle.getString(message.getMessageCode()), params);
        }
        return getExceptionMessage(message.getDefaultMessage(), params);
    }

    /**
     * Get exception message
     *
     * @param message {@link ExceptionMessage}
     * @param params  params for format exception message
     */
    private static String getExceptionMessage(String message, String... params) {
        return MessageFormat.format(message, (Object[]) params);
    }

    /**
     * Get {@link ResourceBundle} with error messages for current locale
     */
    private static ResourceBundle getErrorResourceBundle() {
        return getErrorResourceBundle(LocaleContextHolder.getLocale());
    }

    /**
     * Get {@link ResourceBundle} with error messages for language
     *
     * @param language language
     */
    private static ResourceBundle getErrorResourceBundle(String language) {
        return getErrorResourceBundle(new Locale(language));
    }

    /**
     * Get {@link ResourceBundle} with error messages for locale
     *
     * @param locale locale
     */
    private static ResourceBundle getErrorResourceBundle(Locale locale) {
        if (!getErrorResourceBundles().containsKey(locale)) {
            ResourceBundle errorResourceBundle = ResourceBundle.getBundle(EXCEPTION_MESSAGES_PROPERTIES, locale);
            getErrorResourceBundles().put(locale.getLanguage(), errorResourceBundle);
            return errorResourceBundle;
        }
        return getErrorResourceBundles().get(locale.getLanguage());
    }

    /**
     * Get all {@link ResourceBundle} with error messages
     */
    private static Map<String, ResourceBundle> getErrorResourceBundles() {
        if (null == errorResourceBundles) {
            errorResourceBundles = Maps.newHashMap();
        }
        return errorResourceBundles;
    }
}