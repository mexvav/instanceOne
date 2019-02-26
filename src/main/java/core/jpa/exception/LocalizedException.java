package core.jpa.exception;

import com.google.common.collect.Maps;
import core.jpa.Constants;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Exception for Runtime Entity
 */
public class LocalizedException extends RuntimeException {

    /**
     * Name of exception property file
     */
    private static final String EXCEPTION_MESSAGES_PROPERTIES = "exception_message";

    /**
     * Exception messages in different locales
     */
    private static Map<String, ResourceBundle> errorResourceBundles;


    /**
     * Params of localized message
     */
    private ExceptionMessage exceptionMessage;

    /**
     * Params for format exception message
     */
    private String[] params;

    /**
     * Constructs a new not localized exception with the specified detail message
     */
    public LocalizedException(String message) {
        super(message);
    }

    /**
     * Constructs a new not localized exception with the specified detail message and cause
     */
    public LocalizedException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new localized exception with the specified detail message
     * in current locale
     */
    public LocalizedException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new localized exception with the specified detail message
     * in current locale
     *
     * @param exceptionMessage the params of localized message
     * @param params           the params for format exception message
     */
    public LocalizedException(ExceptionMessage exceptionMessage, String... params) {
        super();
        this.exceptionMessage = exceptionMessage;
        this.params = params;
    }

    /**
     * Constructs a new localized exception with the specified detail message and cause
     *
     * @param exceptionMessage the params of localized message
     * @param cause            the cause of exception
     * @param params           params for format exception message
     */
    public LocalizedException(ExceptionMessage exceptionMessage, Throwable cause, String... params) {
        super(cause);
        this.exceptionMessage = exceptionMessage;
        this.params = params;
    }

    /**
     * Get exception message
     *
     * @param message  {@link ExceptionMessage}
     * @param language language for exception
     * @param params   params for format exception message
     */
    private static String getExceptionMessage(@Nullable ExceptionMessage message, @Nullable String language, String... params) {
        if (null == message || null == language) {
            return Constants.EMPTY;
        }
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
    private static String getExceptionMessage(@Nullable String message, String... params) {
        if (null == message) {
            return Constants.EMPTY;
        }
        return MessageFormat.format(message, (Object[]) params);
    }

    /**
     * Get {@link ResourceBundle} with error messages for language
     *
     * @param language language
     */
    private static ResourceBundle getErrorResourceBundle(@NotNull String language) {
        return getErrorResourceBundle(new Locale(Objects.requireNonNull(language)));
    }

    /**
     * Get {@link ResourceBundle} with error messages for locale
     *
     * @param locale locale
     */
    private static ResourceBundle getErrorResourceBundle(@NotNull Locale locale) {
        if (!getErrorResourceBundles().containsKey(Objects.requireNonNull(locale.getLanguage()))) {
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

    /**
     * Returns the detail message string of this Exception for current locale
     *
     * @return the detail message string of this {@link LocalizedException} instance
     */
    @Override
    public String getMessage() {
        return getMessage(LocaleContextHolder.getLocale().getLanguage());
    }

    /**
     * Returns the detail message string of this Exception for locale
     *
     * @param language language for exception
     * @return the detail message string of this {@link LocalizedException} instance
     */
    public String getMessage(@Nullable String language) {
        if (null == exceptionMessage || null == language) {
            return super.getMessage();
        }
        return getExceptionMessage(exceptionMessage, language, params);
    }

    /**
     * Returns the detail message string of this Exception for current locale
     *
     * @return the detail message string of this {@link LocalizedException} instance
     */
    @Override
    public String getLocalizedMessage() {
        return getMessage();
    }

    /**
     * Returns the detail message string of this Exception for locale
     *
     * @param language language for exception
     * @return the detail message string of this {@link LocalizedException} instance
     */
    public String getLocalizedMessage(@Nullable String language) {
        return getMessage(language);
    }
}