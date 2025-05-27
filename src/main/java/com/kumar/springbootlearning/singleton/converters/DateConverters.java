package com.kumar.springbootlearning.singleton.converters;

import org.springframework.core.convert.converter.Converter;

import java.time.*;
import java.util.Date;

public class DateConverters {

    public static class ZonedDateTimeToDateConverter implements Converter<ZonedDateTime, Date> {

        public static final ZonedDateTimeToDateConverter INSTANCE = new ZonedDateTimeToDateConverter();

        private ZonedDateTimeToDateConverter() {
        }

        @Override
        public Date convert(ZonedDateTime source) {
            return source == null ? null : Date.from(source.toInstant());
        }
    }

    public static class DateToZonedDateTimeConverter implements Converter<Date, ZonedDateTime> {

        public static final DateToZonedDateTimeConverter INSTANCE = new DateToZonedDateTimeConverter();

        private DateToZonedDateTimeConverter() {
        }

        @Override
        public ZonedDateTime convert(Date source) {
            return source == null ? null : ZonedDateTime.ofInstant(source.toInstant(), ZoneId.systemDefault());
        }
    }

    public static class OffsetDateTimeToDateConverter implements Converter<OffsetDateTime, Date> {

        public static final OffsetDateTimeToDateConverter INSTANCE = new OffsetDateTimeToDateConverter();

        private OffsetDateTimeToDateConverter() {
        }

        @Override
        public Date convert(OffsetDateTime source) {
            return source == null ? null : Date.from(source.toInstant());
        }
    }

    public static class DateToOffsetDateTimeConverter implements Converter<Date, OffsetDateTime> {

        public static final DateToOffsetDateTimeConverter INSTANCE = new DateToOffsetDateTimeConverter();

        private DateToOffsetDateTimeConverter() {
        }

        @Override
        public OffsetDateTime convert(Date source) {
            return source == null ? null : OffsetDateTime.ofInstant(source.toInstant(), ZoneId.systemDefault());
        }
    }

    public static class LongToUTCLocalDateConverter implements Converter<Long, LocalDate> {

        public static final LongToUTCLocalDateConverter INSTANCE = new LongToUTCLocalDateConverter();

        private LongToUTCLocalDateConverter() {
        }

        @Override
        public LocalDate convert(Long source) {
            return source == null ? null : LocalDate.ofInstant(Instant.ofEpochMilli(source), ZoneId.of("UTC"));
        }
    }

    public static class OffseTimeToStringConverter implements Converter<OffsetTime, String> {

        public static final OffseTimeToStringConverter INSTANCE = new OffseTimeToStringConverter();

        private OffseTimeToStringConverter() {
        }

        @Override
        public String convert(OffsetTime source) {
            return source == null ? null : source.toString();
        }
    }

    public static class StringToOffseTimeConverter implements Converter<String, OffsetTime> {

        public static final StringToOffseTimeConverter INSTANCE = new StringToOffseTimeConverter();

        private StringToOffseTimeConverter() {
        }

        @Override
        public OffsetTime convert(String source) {
            return source == null ? null : OffsetTime.parse(source);
        }
    }
}