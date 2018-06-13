package com.processor;

import com.model.ethereum.EthereumTransaction;
import com.model.report.DailyReport;
import com.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by alejandrosantamaria on 09/06/18.
 */
@Slf4j
@Service
public class PositionCalculator {

    public Map<LocalDate, List<EthereumTransaction>> getMapListDailyTransactions(
            List<EthereumTransaction> transactionList) {

        Map<LocalDate, List<EthereumTransaction>> mapDailyTx = transactionList.stream()
                .collect(Collectors.groupingBy(ethereumTransaction ->
                        DateTimeUtils.unixTimestampToLocalDate(ethereumTransaction.getTimeStamp())));

        return mapDailyTx;
    }

    public Map<LocalDate, DailyReport> generateFullReport(List<EthereumTransaction> txList,
                    String address, Map<LocalDate,
                    List<EthereumTransaction>> mapDailyTransactions){

        Map<LocalDate, DailyReport> dailyReportMap = new HashMap<>();

        LocalDate dateIterator = DateTimeUtils.unixTimestampToLocalDate(txList.get(0).getTimeStamp());
        LocalDate lastDate = DateTimeUtils.unixTimestampToLocalDate(txList.get(txList.size()-1).getTimeStamp());

        BigInteger total = new BigInteger("0");
        for(;!dateIterator.isAfter(lastDate); dateIterator = dateIterator.plusDays(1)){
            List<EthereumTransaction> dayTxs = mapDailyTransactions.get(dateIterator);

            if ( dayTxs != null ) {
                BigInteger dayTotal = sumTransactions(dayTxs, address);
                total = total.add(dayTotal);

                DailyReport dailyReport = new DailyReport();
                dailyReport.setTransactionList(dayTxs);
                dailyReport.setTotalBalance(total);
                dailyReport.setDayBalance(dayTotal);

                dailyReportMap.put(dateIterator, dailyReport);
            }
        }

        return dailyReportMap;
    }

    private BigInteger sumTransactions(List<EthereumTransaction> transactionList, String address){
        BigInteger total = transactionList.stream()
                .map(ethereumTransaction -> {
                    if (ethereumTransaction.getFrom().equals(address)) {
                        long txFeeGas = -1 * ethereumTransaction.getGasUsed() * ethereumTransaction.getGasPrice();
                        return ethereumTransaction.getValue().negate().add(BigInteger.valueOf(txFeeGas));
                    }
                    return ethereumTransaction.getValue();
                })
                .reduce(BigInteger.ZERO, BigInteger::add);

        return total;
    }
}
