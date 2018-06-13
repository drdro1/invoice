package com.processor;

import com.model.ethereum.EthereumTransaction;
import com.model.ethereum.EthereumTransactionsMessage;
import com.model.invoice.Period;
import com.model.invoice.input.Account;
import com.model.invoice.input.CountryTax;
import com.model.invoice.input.InvoiceQuery;
import com.model.invoice.output.*;
import com.model.report.DailyReport;
import com.provider.EthereumTransactionProvider;
import com.provider.ExchangeProvider;
import com.utils.EthereumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by alejandrosantamaria on 11/06/18.
 */
@Service
public class VaultInvoicer {
    @Autowired ExchangeProvider exchangeProvider;
    @Autowired EthereumTransactionProvider ethereumTransactionProvider;
    @Autowired PositionCalculator positionCalculator;

    @Value("${flat.month.fee}")
    private Double flatMonthFee;

    @Value("${service.month.fee}")
    private Double serviceMonthFee;

    @Value("${invoice.crypto.currency}")
    private String invoiceCryptoCurrency;

    @Value("${invoice.fiat.currency}")
    private String invoiceFiatCurrency;

    private String isValidPeriod(Period period){
        if ( period == null )
            return "Invoice period cannot be empty";
        if ( period.getStart_date() == null )
            return "Invalid dateStart = empty";
        if ( period.getEnd_date() == null )
            return "Invalid dateEnd = empty";
        else if ( period.getStart_date().isAfter(period.getEnd_date()) ){
            return "Date Start cannot be after Date End";
        }

        return "OK";
    }

    private String checkValidInput(Period period, Account account, CountryTax countryTax){
        String validRangeMsg = isValidPeriod(period);

        if ( !validRangeMsg.equals("OK") )
            return validRangeMsg;

        if ( countryTax == null )
            return "Please specify tax rate";

        if ( account == null )
            return "Please provide an account to process";

        //First check if addresses are valid
        for(String address : account.getAddresses() )
            if ( !EthereumUtils.isValidAddress(address) )
                return "Invalid ethereum address: " + address;

        return "OK";
    }

    public ResponseEntity getVaultInvoice(InvoiceQuery invoiceQuery){
        Period period = invoiceQuery.getPeriod();
        Account account = invoiceQuery.getAccount();
        CountryTax countryTax = invoiceQuery.getTax();

        String validInputMsg = checkValidInput(period, account, countryTax);
        if ( !validInputMsg.equals("OK") )
            return new ResponseEntity(validInputMsg, HttpStatus.BAD_REQUEST);


        List<LineItem> lineItemList = new ArrayList<>();
        lineItemList.add(new LineItem(LineItemType.FLAT_FEE, new InvoicePrice(flatMonthFee, invoiceFiatCurrency)));

        for(String address : account.getAddresses() ){
            EthereumTransactionsMessage etherscanTransactions =
                    ethereumTransactionProvider.getEtherscanTransactions(address);

            List<EthereumTransaction> txList = etherscanTransactions.getResult();
            if ( !txList.isEmpty() ) {
                Map<LocalDate, List<EthereumTransaction>> dailyTxMapList =
                        positionCalculator.getMapListDailyTransactions(txList);

                Map<LocalDate, DailyReport> fullReportMap =
                        positionCalculator.generateFullReport(txList, address, dailyTxMapList);

                double averageFiatPosition =
                        calculateAverageFiatPosition(period.getStart_date(), period.getEnd_date(), fullReportMap);

                double cost = averageFiatPosition * serviceMonthFee;

                lineItemList.add(new LineItemService(
                        LineItemType.SERVICE_FEE,
                        new InvoicePrice(cost, invoiceFiatCurrency),
                        new Item(invoiceCryptoCurrency, address, period,
                                new InvoicePrice(averageFiatPosition, invoiceFiatCurrency))));
            }
        }

        double sumFees = lineItemList.stream().mapToDouble(lineItem -> lineItem.getPrice().getAmount()).sum();

        double taxCost = sumFees * countryTax.getRate();
        lineItemList.add(new LineItem(LineItemType.TAX_FEE, new InvoicePrice(taxCost, invoiceFiatCurrency)));

        Invoice invoice = new Invoice(new InvoicePrice(sumFees + taxCost, invoiceFiatCurrency), lineItemList);

        return new ResponseEntity(invoice, HttpStatus.OK);
    }

    private double calculateAverageFiatPosition(LocalDate startDate,
                                          LocalDate endDate,
                                          Map<LocalDate, DailyReport> fullReportMap){
        double totalFiatValue = 0.0;
        double totalDays = (double)java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1;

        for(LocalDate iterator = startDate; !iterator.isAfter(endDate); iterator = iterator.plusDays(1)) {
            if ( fullReportMap.containsKey(iterator) ) {
                double etherUsd = exchangeProvider.getEtherUsd(iterator);

                BigInteger weiDayBalance = fullReportMap.get(iterator).getTotalBalance();
                double ethDayBalance = EthereumUtils.getEthereumFromWei(weiDayBalance);
                totalFiatValue += etherUsd * ethDayBalance;
            }
        }

        return totalFiatValue/totalDays;
    }
}
