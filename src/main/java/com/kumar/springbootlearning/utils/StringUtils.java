package com.kumar.springbootlearning.utils;


public final class StringUtils {

    private static final String CAMELCASE_SPLIT_REGEX = "([a-z])([A-Z]+)";

    private StringUtils() {
    }

    public static String camelToSnake(String str) {
        // Replace the given regex with replacement string
        // and convert it to lower case.
        return str.replaceAll(CAMELCASE_SPLIT_REGEX, "$1_$2").toLowerCase();
    }

    /**
     * Concatenates an array of strings with a delimiter.
     *
     * @param array     the array of strings to concatenate
     * @param delimiter the delimiter to separate the strings
     * @return the concatenated string
     */
    public static String concatenateArray(String[] array, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i > 0 && delimiter != null) {
                sb.append(delimiter);
            }
            sb.append(array[i]);
        }
        return sb.toString();
    }
}