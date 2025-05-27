package com.kumar.springbootlearning.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

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
            DateTimeFormatter formatter = getDateTimeFormatter(format);
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            log.error("Error parsing date time string: {}", e.getMessage());
        }
        return null;
    }

    public static List<ImmutablePair<String, String>> getTimeZones() {
        String[] ids = TimeZone.getAvailableIDs();
        List<ImmutablePair<String, String>> timeZoneList = new ArrayList<>();
        for (String id : ids) {
            timeZoneList.add(new ImmutablePair<>(id, displayTimeZone(TimeZone.getTimeZone(id))));
        }
        return timeZoneList;
    }

    private static String displayTimeZone(TimeZone tz) {
        long hours = TimeUnit.MILLISECONDS.toHours(tz.getRawOffset());
        long minutes = TimeUnit.MILLISECONDS.toMinutes(tz.getRawOffset()) - TimeUnit.HOURS.toMinutes(hours);
        // avoid -4:-30 issue
        minutes = Math.abs(minutes);
        String result;
        if (hours >= 0) {
            result = String.format("(GMT+%d:%02d) %s", hours, minutes, tz.getID());
        } else {
            result = String.format("(GMT%d:%02d) %s", hours, minutes, tz.getID());
        }
        return result;
    }

    /**
     * Provides a case-insensitive version of DateTimeFormatter for the "format"
     * @param format - valid date time format
     * @return case-insensitive version of DateTimeFormatter
     */
    public static DateTimeFormatter getDateTimeFormatter(String format) {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        builder.parseCaseInsensitive();
        builder.appendPattern(format);
        return builder.toFormatter();
    }
}
