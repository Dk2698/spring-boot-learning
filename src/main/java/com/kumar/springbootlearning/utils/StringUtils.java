package com.kumar.springbootlearning.utils;


import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtils {

    public static final String REPLACEMENT = "$1_$2";
    private static final String CAMELCASE_SPLIT_REGEX = "([a-z])([A-Z]+)";

    private StringUtils() {
    }

    public static String camelToSnake(String str) {
        // Replace the given regex with replacement string
        // and convert it to lower case.
        return str.replaceAll(CAMELCASE_SPLIT_REGEX, REPLACEMENT).toLowerCase();
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

    /**
     * Makes an underscored form from the expression in the string (the reverse of the {@link #camelCase(String,
     * boolean, char[]) camelCase} method. Also changes any characters that match the supplied delimiters into
     * underscore. <p> Examples: <p/>
     * <pre>
     *   StringUtils.underscore(&quot;activeRecord&quot;)     #=&gt; &quot;active_record&quot;
     *   StringUtils.underscore(&quot;ActiveRecord&quot;)     #=&gt; &quot;active_record&quot;
     *   StringUtils.underscore(&quot;firstName&quot;)        #=&gt; &quot;first_name&quot;
     *   StringUtils.underscore(&quot;FirstName&quot;)        #=&gt; &quot;first_name&quot;
     *   StringUtils.underscore(&quot;name&quot;)             #=&gt; &quot;name&quot;
     *   StringUtils.underscore(&quot;The.firstName&quot;)    #=&gt; &quot;the_first_name&quot;
     * </pre>
     * <p/> </p>
     *
     * @param camelCaseWord  the camel-cased word that is to be converted;
     * @param delimiterChars optional characters that are used to delimit word boundaries (beyond capitalization)
     * @return a lower-cased version of the input, with separate words delimited by the underscore character.
     */
    public static String kebabCase(String camelCaseWord, char... delimiterChars) {
        if (camelCaseWord == null) {
            return null;
        }
        String result = camelCaseWord.trim();
        if (result.isEmpty()) {
            return "";
        }
        result = result.replaceAll("([A-Z]+)([A-Z][a-z])", REPLACEMENT);
        result = result.replaceAll("([a-z\\d])([A-Z])", REPLACEMENT);
        result = result.replace('_', '-');
        if (delimiterChars != null) {
            for (char delimiterChar : delimiterChars) {
                result = result.replace(delimiterChar, '-');
            }
        }
        return result.toLowerCase();
    }

    /**
     * Returns a copy of the input with the first character converted to uppercase and the remainder to lowercase.
     *
     * @param words the word to be capitalized
     * @return the string with the first character capitalized and the remaining characters lowercased
     */
    public static String capitalize(String words) {
        if (words == null) {
            return null;
        }
        String result = words.trim();
        if (result.isEmpty()) {
            return "";
        }
        if (result.length() == 1) {
            return result.toUpperCase();
        }
        return "" + Character.toUpperCase(result.charAt(0)) + result.substring(1).toLowerCase();
    }

    /**
     * By default, this method converts strings to UpperCamelCase. If the <code>uppercaseFirstLetter</code> argument to
     * false, then this method produces lowerCamelCase. This method will also use any extra delimiter characters to
     * identify word boundaries. <p> Examples: <p/>
     * <pre>
     *   StringUtils.camelCase(&quot;active_record&quot;,false)    #=&gt; &quot;activeRecord&quot;
     *   StringUtils.camelCase(&quot;active_record&quot;,true)     #=&gt; &quot;ActiveRecord&quot;
     *   StringUtils.camelCase(&quot;first_name&quot;,false)       #=&gt; &quot;firstName&quot;
     *   StringUtils.camelCase(&quot;first_name&quot;,true)        #=&gt; &quot;FirstName&quot;
     *   StringUtils.camelCase(&quot;name&quot;,false)             #=&gt; &quot;name&quot;
     *   StringUtils.camelCase(&quot;name&quot;,true)              #=&gt; &quot;Name&quot;
     * </pre>
     * <p/> </p>
     *
     * @param lowerCaseAndUnderscoredWord the word that is to be converted to camel case
     * @param uppercaseFirstLetter        true if the first character is to be uppercased, or false if the first character is
     *                                    to be lowercased
     * @param delimiterChars              optional characters that are used to delimit word boundaries
     * @return the camel case version of the word
     * @see #snakeCase (String, char[])
     */
    public static String camelCase(String lowerCaseAndUnderscoredWord, boolean uppercaseFirstLetter, char... delimiterChars) {
        if (lowerCaseAndUnderscoredWord == null) {
            return null;
        }
        lowerCaseAndUnderscoredWord = lowerCaseAndUnderscoredWord.trim();
        if (lowerCaseAndUnderscoredWord.isEmpty()) {
            return "";
        }
        if (uppercaseFirstLetter) {
            String result = lowerCaseAndUnderscoredWord;
            // Replace any extra delimiters with underscores (before the
            // underscores are converted in the next step)...
            if (delimiterChars != null) {
                for (char delimiterChar : delimiterChars) {
                    result = result.replace(delimiterChar, '_');
                }
            }

            // Change the case at the beginning at after each underscore ...
            return replaceAllWithUppercase(result);
        }
        if (lowerCaseAndUnderscoredWord.length() < 2) {
            return lowerCaseAndUnderscoredWord;
        }
        return "" + Character.toLowerCase(lowerCaseAndUnderscoredWord.charAt(0)) +
                camelCase(lowerCaseAndUnderscoredWord, true, delimiterChars).substring(1);
    }

    /**
     * Utility method to replace all occurrences given by the specific backreference with its uppercased form, and
     * remove all other backreferences. <p> The Java {@link Pattern regular expression processing} does not use the
     * preprocessing directives <code>\l</code>, <code>&#92;u</code>, <code>\L</code>, and <code>\U</code>. If so, such
     * directives could be used in the replacement string to uppercase or lowercase the backreferences. For example,
     * <code>\L1</code> would lowercase the first backreference, and <code>&#92;u3</code> would uppercase the 3rd
     * backreference. </p>
     *
     * @return the input string with the appropriate characters converted to upper-case
     */
    private static String replaceAllWithUppercase(String input) {
        Pattern underscoreAndDotPattern = Pattern.compile("(^|_)(.)");
        Matcher matcher = underscoreAndDotPattern.matcher(input);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(2).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * Makes an underscored form from the expression in the string (the reverse of the {@link #camelCase(String,
     * boolean, char[]) camelCase} method. Also changes any characters that match the supplied delimiters into
     * underscore. <p> Examples: <p/>
     * <pre>
     *   StringUtils.underscore(&quot;activeRecord&quot;)     #=&gt; &quot;active_record&quot;
     *   StringUtils.underscore(&quot;ActiveRecord&quot;)     #=&gt; &quot;active_record&quot;
     *   StringUtils.underscore(&quot;firstName&quot;)        #=&gt; &quot;first_name&quot;
     *   StringUtils.underscore(&quot;FirstName&quot;)        #=&gt; &quot;first_name&quot;
     *   StringUtils.underscore(&quot;name&quot;)             #=&gt; &quot;name&quot;
     *   StringUtils.underscore(&quot;The.firstName&quot;)    #=&gt; &quot;the_first_name&quot;
     * </pre>
     * <p/> </p>
     *
     * @param camelCaseWord  the camel-cased word that is to be converted;
     * @param delimiterChars optional characters that are used to delimit word boundaries (beyond capitalization)
     * @return a lower-cased version of the input, with separate words delimited by the underscore character.
     */
    public static String snakeCase(String camelCaseWord, char... delimiterChars) {
        if (camelCaseWord == null) {
            return null;
        }
        String result = camelCaseWord.trim();
        if (result.isEmpty()) {
            return "";
        }
        result = result.replaceAll("([A-Z]+)([A-Z][a-z])", REPLACEMENT);
        result = result.replaceAll("([a-z\\d])([A-Z])", REPLACEMENT);
        result = result.replace('-', '_');
        if (delimiterChars != null) {
            for (char delimiterChar : delimiterChars) {
                result = result.replace(delimiterChar, '_');
            }
        }
        return result.toLowerCase();
    }

    /**
     * Replaces placeholders in the format {key} in the input string
     * using values from the given parameters map.
     *
     * @param template the string containing placeholders like {param}
     * @param params the map of placeholder names to values
     * @return the resolved string
     */
    public static String replacePlaceholders(String template, Map<String, String> params) {
        if (template == null || params == null || params.isEmpty()) {
            return template;
        }

        StringBuffer result = new StringBuffer();
        Pattern pattern = Pattern.compile("\\{(\\w+)}");
        Matcher matcher = pattern.matcher(template);

        while (matcher.find()) {
            String key = matcher.group(1);
            String replacement = params.getOrDefault(key, matcher.group(0)); // keep original if not found
            matcher.appendReplacement(result, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(result);

        return result.toString();
    }
}
