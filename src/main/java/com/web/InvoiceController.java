package com.web;

import com.model.invoice.InvoiceQuery;
import com.processor.VaultInvoicer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by alejandrosantamaria on 12/06/18.
 */
@RestController()
public class InvoiceController {
    @Autowired VaultInvoicer vaultInvoicer;

    @PostMapping(value = "/invoices")
    public ResponseEntity<Object> generateInvoices(@RequestBody InvoiceQuery invoiceQuery){
        return vaultInvoicer.getVaultInvoice(invoiceQuery);
    }
}
