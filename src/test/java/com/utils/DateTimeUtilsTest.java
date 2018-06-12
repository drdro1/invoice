package com.utils;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

/**
 * Created by alejandrosantamaria on 11/06/18.
 */
public class DateTimeUtilsTest extends TestCase {
    /**
     * All the timestamps here were verified with https://www.unixtimestamp.com/index.php
     * @throws Exception
     */


    @Test
    public void testUnixTimestampToLocalDate() throws Exception {
        LocalDate target = LocalDate.of(2018, 6, 12);
        long unixTimestamp = 1528838175; //12/June/2018 @ 9:16pm (UTC)
        LocalDate ld = DateTimeUtils.unixTimestampToLocalDate(unixTimestamp);
        Assert.assertTrue(ld.equals(target));

        unixTimestamp += 60*60*2;  //12/June/2018 @ 11:16pm (UTC)
        ld = DateTimeUtils.unixTimestampToLocalDate(unixTimestamp);
        Assert.assertTrue(ld.equals(target));

        unixTimestamp += 60*60*1;  //13/June/2018 @ 00:16am (UTC)
        ld = DateTimeUtils.unixTimestampToLocalDate(unixTimestamp);
        Assert.assertFalse(ld.equals(target));
    }

    @Test
    public void testLocalDateToTimestamp() throws Exception {
        LocalDate localDate = LocalDate.of(2018, 6, 12);
        long target = 1528804800;   //12/June/2018 @ 12:00pm (UTC)

        long timestamp = DateTimeUtils.localDateToTimestamp(localDate);

        Assert.assertEquals(timestamp,  target);
    }
}