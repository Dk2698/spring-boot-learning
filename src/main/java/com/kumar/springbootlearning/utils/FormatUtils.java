package com.kumar.springbootlearning.utils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Slf4j
@SuppressWarnings("unused")
public final class FormatUtils {

    private static final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();


    private FormatUtils() {
    }

    public static String maskMobileNumber(String mobileNumber) {
        if (mobileNumber == null || mobileNumber.length() < 4) {
            return mobileNumber;
        }
        String lastFour = mobileNumber.substring(mobileNumber.length() - 4);
        String masked = "*".repeat(mobileNumber.length() - 4);
        return masked + lastFour;
    }

    public static String formatContactNumber(String phoneNumber, String regionCode) {
        // Validate input parameters
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return phoneNumber;
        }
        try {
            log.debug("Processing phone number: {} for region: {}", phoneNumber, regionCode);
            // Parse the phone number (with or without region code)
            Phonenumber.PhoneNumber number = regionCode != null ?
                    phoneNumberUtil.parse(phoneNumber, regionCode) :
                    phoneNumberUtil.parse(phoneNumber, "");
            log.debug("Parsed phone number: {}", number);
            // Validate using only ValidationResult
            PhoneNumberUtil.ValidationResult validationResult = phoneNumberUtil.isPossibleNumberWithReason(number);
            if (validationResult != PhoneNumberUtil.ValidationResult.IS_POSSIBLE) {
                log.error("Invalid phone number: {}. Reason: {}", phoneNumber, validationResult);
                throw new IllegalArgumentException("Phone# is not valid due to " + validationResult);
            }
            String formattedNumber = phoneNumberUtil.format(number, PhoneNumberUtil.PhoneNumberFormat.E164);
            log.debug("Successfully formatted phone number: {} to E.164 format: {}", phoneNumber, formattedNumber);
            return formattedNumber;
        } catch (NumberParseException e) {
            String errorMsg = String.format("Failed to parse phone number: %s. Error: %s", phoneNumber, e.getMessage());
            log.error(errorMsg, e);
            throw new IllegalArgumentException(errorMsg, e);
        }
    }

    public static String formatDate(Object value, String defaultDateFormat, String defaultTimezone) {
        try {
            Instant instant = value instanceof Long longValue
                    ? Instant.ofEpochMilli(longValue)
                    : Instant.parse(value.toString());
            LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.of(defaultTimezone));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(defaultDateFormat);
            return dateTime.format(formatter);
        } catch (Exception e) {
            log.error("Error formatting date", e);
            return value.toString();
        }
    }

    public static String formatTime(Object value, String format, String defaultTimezone) {
        try {
            Instant instant = value instanceof Long longValue
                    ? Instant.ofEpochMilli(longValue)
                    : Instant.parse(value.toString());
            LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.of(defaultTimezone));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format != null ? format : "HH:mm:ss");
            return dateTime.format(formatter);
        } catch (Exception e) {
            log.error("Error formatting time", e);
            return value.toString();
        }
    }

    public static String formatNumeric(Object value) {
        if (value instanceof Double) {
            return String.format("%.2f", value);
        } else if (value instanceof Integer || value instanceof Long) {
            return value.toString();
        } else {
            return "";
        }
    }
}
