package kr.co.ync.controller.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class util {
    public static LocalDate parseToLocalDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }
}
