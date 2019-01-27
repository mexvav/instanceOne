package core.utils;

public class StringUtils {

    public static String capitalizeFirstLetter(String value) {
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }
}
