package kr.co.ync.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Util {
    public static LocalDate parseToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }
}
