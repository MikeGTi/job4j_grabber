package ru.job4j.grabber.utils;

import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HabrCareerDateTimeParserTest {
    @Test
    public void whenNowThenNow() {
        String nowDate = LocalDateTime.now().toString();
        HabrCareerDateTimeParser dateParser = new HabrCareerDateTimeParser();
        assertThat(dateParser.parse(nowDate)).isEqualTo(nowDate);
    }

    @Test
    public void whenDate1ThenDate1() {
        String date = String.format("%sT%s",  Date.valueOf("2004-02-01"), Time.valueOf("22:01:04"));
        HabrCareerDateTimeParser dateParser = new HabrCareerDateTimeParser();
        assertThat(dateParser.parse(date)).isEqualTo("2004-02-01T22:01:04");
    }

    @Test
    public void whenDate2ThenDate2() {
        String date = String.format("%sT%s",  Date.valueOf("0001-01-01"), Time.valueOf("01:00:00"));
        HabrCareerDateTimeParser dateParser = new HabrCareerDateTimeParser();
        assertThat(dateParser.parse(date)).isEqualTo("0001-01-01T01:00:00");
    }

    @Test
    public void whenBlankDateThenDateTimeParseException() {
        HabrCareerDateTimeParser dateParser = new HabrCareerDateTimeParser();
        assertThrows(DateTimeParseException.class, () -> dateParser.parse(""));
    }

    @Test
    public void whenNullDateThenNullPointerException() {
        HabrCareerDateTimeParser dateParser = new HabrCareerDateTimeParser();
        assertThrows(NullPointerException.class, () -> dateParser.parse(null));
    }
}