package com.utils;

import java.time.*;

/**
 * Created by alejandrosantamaria on 10/06/18.
 */
public class DateTimeUtils {

    private static ZoneId ZONE_UTC = ZoneId.of("UTC");

    public static LocalDate unixTimestampToLocalDate(long unixTimestamp){
        LocalDate ld = Instant.ofEpochMilli(unixTimestamp * 1000).atZone(ZONE_UTC).toLocalDate();
        return ld;
    }

    public static long localDateToTimestamp(LocalDate d){
        return LocalDateTime.of(d.getYear(), d.getMonthValue(), d.getDayOfMonth(), 12, 0).toEpochSecond(ZoneOffset.UTC);
    }
}
