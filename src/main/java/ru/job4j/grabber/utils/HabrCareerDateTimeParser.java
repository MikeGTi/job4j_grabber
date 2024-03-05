package ru.job4j.grabber.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HabrCareerDateTimeParser implements DateTimeParser {

    @Override
    public LocalDateTime parse(String parse) {
        /*if (parse.isBlank()) {
            throw new DateTimeParseException("Date string is blank", parse, 0);
        }*/
        return LocalDateTime.parse(parse, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public static void main(String[] args) {
        HabrCareerDateTimeParser parser = new HabrCareerDateTimeParser();
        /*String testDateTime = LocalDateTime.now().toString();
        System.out.println(testDateTime);
        System.out.println(parser.parse(testDateTime));*/

        System.out.println(parser.parse(""));
    }
}