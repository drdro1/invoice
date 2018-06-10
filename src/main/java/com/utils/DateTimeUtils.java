package com.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Created by alejandrosantamaria on 10/06/18.
 */
public class DateTimeUtils {

    public static LocalDate unixTimestampToLocalDate(long unixTimestamp){
        LocalDate ld = Instant.ofEpochMilli(unixTimestamp * 1000).atZone(ZoneId.of("UTC")).toLocalDate();
        return ld;
    }

    public static long localDateToTimestamp(LocalDate localDate){
        return localDate.atStartOfDay(ZoneId.of("UTC")).toEpochSecond();
    }
}
