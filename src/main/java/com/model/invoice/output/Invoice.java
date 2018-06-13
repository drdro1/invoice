package com.model.invoice.output;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Created by alejandrosantamaria on 13/06/18.
 */
@Data
@AllArgsConstructor
public class Invoice {
    private InvoicePrice total;
    private List<LineItem> lineItemList;
}
