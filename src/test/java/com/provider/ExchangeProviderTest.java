package com.provider;

import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

/**
 * Created by alejandrosantamaria on 11/06/18.
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ExchangeProviderTest extends TestCase {
    @Autowired private ExchangeProvider exchangeProvider;

    @Test
    public void testGetEtherUsd() throws Exception {
        Double ethUsd = exchangeProvider.getEtherUsd(LocalDate.of(2018, 6, 7));
        Assert.assertEquals(ethUsd, 0.4, 0.001);
    }

    @Test
    public void testCache() throws Exception {
        long millis = System.currentTimeMillis();
        Double ethUsd = exchangeProvider.getEtherUsd(LocalDate.of(2018, 6, 7));
        long timeNoCache = System.currentTimeMillis()-millis;
        log.info("TimeNoCache={}", (timeNoCache));
        Thread.sleep(1000);

        millis = System.currentTimeMillis();
        ethUsd = exchangeProvider.getEtherUsd(LocalDate.of(2018, 6, 7));
        long timeWithCache = System.currentTimeMillis()-millis;
        log.info("TimeWithCache={}", timeWithCache);

        Assert.assertTrue(timeNoCache > (10 * timeWithCache));
    }

    @Test
    public void testGetMultipleEtherUsd() throws Exception {
        LocalDate startDate = LocalDate.of(2013, 1, 1);
        LocalDate endDate = LocalDate.now();

        long millis = System.currentTimeMillis();
        for(LocalDate iterator = startDate; iterator.isBefore(endDate); iterator = iterator.plusDays(1)) {
            Double ethUsd = exchangeProvider.getEtherUsd(iterator);
            Assert.assertNotNull(ethUsd);
            System.out.println(iterator + "," + ethUsd);
            Thread.sleep(1000);
        }
        log.info("Test time {}", System.currentTimeMillis()-millis);
    }
}