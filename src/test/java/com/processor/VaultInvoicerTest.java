package com.processor;

import com.google.common.collect.Lists;
import com.model.invoice.Period;
import com.model.invoice.input.Account;
import com.model.invoice.input.CountryTax;
import com.model.invoice.input.InvoiceQuery;
import com.model.invoice.output.Invoice;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
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
    private static String testAddress1 = "0x281055afc982d96fab65b3a49cac8b878184cb16";
    private static String testAddress2 = "0x98e4ea439617ddd70ca66d41a543fefd931ebee0";

    @Autowired VaultInvoicer vaultInvoicer;

    @Test
    public void testGetVaultInvoice() throws Exception {
        InvoiceQuery invoiceQuery = new InvoiceQuery();
        invoiceQuery.setPeriod(new Period(LocalDate.of(2018, 3, 1), LocalDate.of(2018, 3, 31)));
        invoiceQuery.setTax(CountryTax.france);

        Account account = new Account();
        account.setName("test");
        account.setCurrency("ethereum");
        account.setAddresses(Lists.newArrayList(testAddress1, testAddress2));


        invoiceQuery.setAccount(account);

        ResponseEntity<Invoice> responseEntity = vaultInvoicer.getVaultInvoice(invoiceQuery);

        Invoice invoice = responseEntity.getBody();

        Assert.assertEquals(invoice.getTotal().getAmount(), 57405424.136767544, 0.1);
    }
}