package com.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Created by alejandrosantamaria on 10/06/18.
 */
public class DateTimeUtils {

    private static ZoneId ZONE_UTC = ZoneId.of("UTC");

    public static LocalDate unixTimestampToLocalDate(long unixTimestamp){
        LocalDate ld = Instant.ofEpochMilli(unixTimestamp * 1000).atZone(ZONE_UTC).toLocalDate();
        return ld;
    }

    public static long localDateToTimestamp(LocalDate localDate){
        return localDate.atStartOfDay(ZONE_UTC).toEpochSecond();
    }
}
