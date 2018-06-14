package com.provider;

import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Ignore;
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
        Assert.assertEquals(ethUsd, 604.44, 0.001);
    }

    @Ignore
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