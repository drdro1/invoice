package com.model.invoice.output;

import lombok.Data;

/**
 * Created by alejandrosantamaria on 13/06/18.
 */
@Data
public class LineItemService extends LineItem {
    private Item item;

    public LineItemService(LineItemType lineItemType, InvoicePrice invoicePrice, Item item){
        super(lineItemType, invoicePrice);
        this.item = item;
    }
}
