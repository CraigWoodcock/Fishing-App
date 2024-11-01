package com.craigwoodcock.fishingapp.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

@Component
public class DateFormatter {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private static final Logger log = Logger.getLogger(DateFormatter.class.getName());

    public static LocalDateTime formatLocalDateTime(LocalDateTime localDateTime) {
        log.info("LocalDateTime Retrieved:  " + localDateTime);
        String formattedDateTimeString = localDateTime.format(DATE_TIME_FORMATTER);
        LocalDateTime formattedDateTime = LocalDateTime.parse(formattedDateTimeString, DATE_TIME_FORMATTER);
        log.info("Formatted LocalDateTime: " + formattedDateTime);
        return formattedDateTime;
    }
}
