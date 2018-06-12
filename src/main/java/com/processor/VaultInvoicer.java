package com.processor;

import com.model.ethereum.EthereumTransaction;
import com.model.ethereum.EthereumTransactionsMessage;
import com.model.report.Account;
import com.model.report.DailyReport;
import com.model.report.InvoiceQuery;
import com.model.report.Period;
import com.provider.EthereumTransactionProvider;
import com.provider.ExchangeProvider;
import com.utils.EthereumUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
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

    public ResponseEntity getVaultInvoice(InvoiceQuery invoiceQuery){
        Period period = invoiceQuery.getPeriod();
        String validRangeMsg = isValidPeriod(period);
        if ( !validRangeMsg.equals("OK") )
            return new ResponseEntity(validRangeMsg, HttpStatus.BAD_REQUEST);

        if ( invoiceQuery.getTax() == null )
            return new ResponseEntity("Please specify tax rate", HttpStatus.BAD_REQUEST);

        if ( CollectionUtils.isEmpty(invoiceQuery.getAccounts()) ) {
            return new ResponseEntity("Please provide at least one account", HttpStatus.BAD_REQUEST);
        }

        for(Account account : invoiceQuery.getAccounts()){
            //First check if addresses are valid
            for(String address : account.getAddresses() )
                if ( !EthereumUtils.isValidAddress(address) )
                    return new ResponseEntity("Invalid ethereum address: " + address, HttpStatus.BAD_REQUEST);

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
                }
            }
        }

        Double toRet = 0.0;

        return new ResponseEntity(toRet, HttpStatus.OK);
    }

    private double calculateAverageFiatPosition(LocalDate startDate,
                                          LocalDate endDate,
                                          Map<LocalDate, DailyReport> fullReportMap){
        double totalFiatValue = 0.0;
        double totalDays = (double)java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1;

        for(LocalDate iterator = startDate; !iterator.isAfter(endDate); iterator = iterator.plusDays(1)) {
            if ( fullReportMap.containsKey(iterator) ) {
                double etherUsd = exchangeProvider.getEtherUsd(iterator);

                BigInteger weiDayBalance = fullReportMap.get(iterator).getDayBalance();
                double ethDayBalance = EthereumUtils.getEthereumFromWei(weiDayBalance);
                totalFiatValue += etherUsd * ethDayBalance;
            }
        }

        return totalFiatValue/totalDays;
    }
}
