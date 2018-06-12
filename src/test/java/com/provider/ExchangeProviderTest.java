package com.provider;

import junit.framework.TestCase;
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
}