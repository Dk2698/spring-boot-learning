package com.kumar.springbootlearning.mvc.formatter;

import org.springframework.format.datetime.standard.InstantFormatter;

import java.text.ParseException;
import java.time.Instant;
import java.util.Locale;

public class StringInstantFormatter extends InstantFormatter {

    @Override
    public Instant parse(String text, Locale locale) throws ParseException {
        if (text.matches("\\d+")) {
            long epoch = Long.parseLong(text);
            // Adjust here depending on if it's seconds or millis
            return Instant.ofEpochSecond(epoch);
        }
        return super.parse(text, locale);
    }
}