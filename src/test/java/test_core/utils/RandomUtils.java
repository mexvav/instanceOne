package test_core.utils;

import com.google.common.collect.Lists;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class RandomUtils {

    private static final String ENGLISH_ALPHABET =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String CYRILLIC_ALPHABET =
            "АаБбВвГгДдЕеЁёЖжЗзИиЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЪъЫыЬьЭэЮюЯя";
    private static final int CODE_SIZE = 10;
    private static final int TITLE_SIZE = 40;
    private static Random random;
    private static List<String> codes;

    /**
     * Generate boolean value
     *
     * @return random boolean value
     */
    public static boolean getBoolean() {
        return getRandom().nextBoolean();
    }

    /**
     * Generate integer value
     *
     * @return random integer value
     */
    public static int getInteger() {
        return getRandom().nextInt();
    }

    /**
     * Generate date value
     *
     * @return random date value
     */
    public static Date getDate() {
        return new Date();
    }

    /**
     * Generate unique code of standard length. See CODE_SIZE
     *
     * @return unique random code
     */
    public static String getCode() {
        return getCode(CODE_SIZE);
    }

    /**
     * Generate unique code of given length
     *
     * @param count length
     * @return unique random code of given length
     */
    public static String getCode(int count) {
        String generatedCode = generateEnglishString(count);
        if (getCodes().contains(generatedCode)) {
            return getCode(count);
        }
        return generatedCode.toLowerCase();
    }

    public static String getTitle() {
        return generateStringByCustomAlphabet(TITLE_SIZE, ENGLISH_ALPHABET.concat(CYRILLIC_ALPHABET));
    }

    /**
     * Generate random string of given length from English alphabet
     *
     * @param count length
     * @return random string of given length
     */
    public static String generateEnglishString(int count) {
        return generateStringByCustomAlphabet(count, ENGLISH_ALPHABET);
    }

    /**
     * Generate random string of given length from English alphabet
     *
     * @param count length
     * @return random string of given length
     */
    public static String generateCyrillicString(int count) {
        return generateStringByCustomAlphabet(count, CYRILLIC_ALPHABET);
    }

    /**
     * Generate random string of given length from custom alphabet
     *
     * @param count    length
     * @param alphabet alphabet for generating string
     * @return random string of given length
     */
    private static String generateStringByCustomAlphabet(int count, String alphabet) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; ++i) {
            sb.append(alphabet.charAt(getRandom().nextInt(alphabet.length())));
        }
        return sb.toString();
    }

    /**
     * Get {@link Random} instance
     */
    private static Random getRandom() {
        if (null == random) {
            random = new Random();
        }
        return random;
    }

    /**
     * Get generated codes
     *
     * @return unique generated codes
     */
    private static List<String> getCodes() {
        if (null == codes) {
            codes = Lists.newArrayList();
        }
        return codes;
    }
}
