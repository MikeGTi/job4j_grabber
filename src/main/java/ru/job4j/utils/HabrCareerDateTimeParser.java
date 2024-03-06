package ru.job4j.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class HabrCareerDateTimeParser implements DateTimeParser {

    @Override
    public LocalDateTime parse(String value) {
        if (value.isBlank()) {
            throw new DateTimeParseException("Invalid date", value, 400);
        }
        return  (value.contains("+")) ? OffsetDateTime.parse(value).toLocalDateTime()
                                      : LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}