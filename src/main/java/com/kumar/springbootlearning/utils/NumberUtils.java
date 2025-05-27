package com.kumar.springbootlearning.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;

@Slf4j
public final class NumberUtils {

    private static final DecimalFormat df = new DecimalFormat("#.##");
    private static String[] UNITS = {"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
    private static String[] TEENS = {"ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
    private static String[] TENS = {"", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};


    private NumberUtils() {
    }

    public static String convertToIndianWords(double number) {

        if (number == 0) {
            return "zero";
        }

        long intPart = (long) number;
        int decPart = (int) ((number - intPart) * 100);

        String intWords = convertToWords(intPart);
        String decWords = "";
        if (decPart > 0) {
            decWords = " and " + convertToWords(decPart) + " paise";
        }

        return intWords + " rupees" + decWords;
    }

    private static String convertToWords(long number) {
        if (number == 0) {
            return "";
        }

        if (number < 0) {
            return "minus " + convertToWords(-number);
        }

        String words = "";

        if ((number / 10000000) > 0) {
            words += convertToWords(number / 10000000) + " crore ";
            number %= 10000000;
        }

        if ((number / 100000) > 0) {
            words += convertToWords(number / 100000) + " lakh ";
            number %= 100000;
        }

        if ((number / 1000) > 0) {
            words += convertToWords(number / 1000) + " thousand ";
            number %= 1000;
        }

        if ((number / 100) > 0) {
            words += convertToWords(number / 100) + " hundred ";
            number %= 100;
        }

        if (number > 0) {
            if (!words.isEmpty()) {
                words += "and ";
            }
            if (number < 10) {
                words += UNITS[(int) number];
            } else if (number < 20) {
                words += TEENS[(int) (number % 10)];
            } else {
                words += TENS[(int) (number / 10)] + " " + UNITS[(int) (number % 10)];
            }
        }
        return words.trim();
    }

    /**
     * Round the double value to 2 places of decimal
     *
     * @param value value to be rounded
     * @return rounded value
     */
    public static double round(double value) {
        return Double.parseDouble(df.format(value));
    }
}