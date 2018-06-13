package com.model.invoice.output;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by alejandrosantamaria on 13/06/18.
 */
@Data
@AllArgsConstructor
public class InvoicePrice {
    private Double amount;
    private String currency;
}
