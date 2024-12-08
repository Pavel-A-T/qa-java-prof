package com.utils;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class DateComparison {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy", Locale.forLanguageTag("ru"));

    public static LocalDate parseDate(String dateStr) {
        try {
            String datePart = dateStr.split("\\·")[0].trim();
            return LocalDate.parse(datePart, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Ошибка парсинга даты: " + dateStr + ". Причина: " + e.getMessage());
            return null;
        }
    }
}



