package com.processor;

import com.google.common.collect.Lists;
import com.model.invoice.input.Account;
import com.model.invoice.input.CountryTax;
import com.model.invoice.input.InvoiceQuery;
import com.model.invoice.Period;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

/**
 * Created by alejandrosantamaria on 12/06/18.
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class VaultInvoicerTest extends TestCase {
    private static String testAddress = "0x98e4ea439617ddd70ca66d41a543fefd931ebee0";

    @Autowired VaultInvoicer vaultInvoicer;

    @Test
    public void testGetVaultInvoice() throws Exception {
        InvoiceQuery invoiceQuery = new InvoiceQuery();
        invoiceQuery.setPeriod(new Period(LocalDate.of(2018, 2, 1), LocalDate.of(2018, 2, 28)));
        invoiceQuery.setTax(CountryTax.france);

        Account account = new Account();
        account.setName("test");
        account.setCurrency("ethereum");
        account.setAddresses(Lists.newArrayList(testAddress));

        invoiceQuery.setAccount(account);

        ResponseEntity vaultInvoice = vaultInvoicer.getVaultInvoice(invoiceQuery);
    }
}