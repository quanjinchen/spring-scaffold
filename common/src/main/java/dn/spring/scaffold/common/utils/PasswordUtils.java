package dn.spring.scaffold.common.utils;

import java.util.regex.Pattern;

public final class PasswordUtils {

    private static final Pattern NUMBER_PATTERN = Pattern.compile(".*\\d.*");
    private static final Pattern LOWERCASE_PATTERN = Pattern.compile(".*[a-z].*");
    private static final Pattern UPPERCASE_PATTERN = Pattern.compile(".*[A-Z].*");
    private static final Pattern SPECIAL_PATTERN = Pattern.compile(".*[^a-zA-Z0-9].*");

    private PasswordUtils() {
    }

    public static boolean containsNumber(String password) {
        return password != null && NUMBER_PATTERN.matcher(password).matches();
    }

    public static boolean containsLowercase(String password) {
        return password != null && LOWERCASE_PATTERN.matcher(password).matches();
    }

    public static boolean containsUppercase(String password) {
        return password != null && UPPERCASE_PATTERN.matcher(password).matches();
    }

    public static boolean containsSpecialChar(String password) {
        return password != null && SPECIAL_PATTERN.matcher(password).matches();
    }
}
