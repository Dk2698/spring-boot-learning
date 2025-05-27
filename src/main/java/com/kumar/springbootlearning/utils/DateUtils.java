package com.kumar.springbootlearning.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Slf4j
public final class DateUtils {

    private DateUtils() {
    }

    /**
     * Parses the input date string into a LocalDate object based on the provided format.
     *
     * @param dateString the date string to parse
     * @param format     the format pattern for the date string
     * @return the parsed LocalDate object, returns null if parsing fails
     */
    public static LocalDate parseAsLocalDate(String dateString, String format) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            log.error("Error parsing date time string: {}", e.getMessage());
        }
        return null;
    }
}