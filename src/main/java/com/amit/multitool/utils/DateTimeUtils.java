package com.amit.multitool.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class DateTimeUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateTimeUtils.class);

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static final ZoneOffset ZONE_OFFSET = ZoneOffset.UTC;

    public static long getDateTimeFromUser(final String prompt) {
        try {
            final String userInput = ConsoleInputUtils.inputValue(prompt);
            final LocalDateTime dateTime = parseDateTime(userInput);
            return convertToEpochMillis(dateTime);
        } catch (final DateTimeParseException exception) {
            LOGGER.warn(exception.getMessage());
            return getDateTimeFromUser(prompt);
        }
    }

    public static String millisToDateTimeFormat(final long timestamp) {
        return DATE_TIME_FORMATTER.format(Instant.ofEpochMilli(timestamp).atZone(ZONE_OFFSET));
    }

    private static LocalDateTime parseDateTime(final String dateTimeString) throws DateTimeParseException {
        return LocalDateTime.parse(dateTimeString, DATE_TIME_FORMATTER.withZone(ZONE_OFFSET));
    }

    private static long convertToEpochMillis(final LocalDateTime dateTime) {
        return dateTime.atZone(ZONE_OFFSET).toInstant().toEpochMilli();
    }

    private DateTimeUtils() {
        throw new UnsupportedOperationException();
    }
    
}
