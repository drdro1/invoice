package com.processor;

import com.model.DailyReport;
import com.model.Ethereum.EthereumTransaction;
import com.model.FullReport;
import com.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Created by alejandrosantamaria on 09/06/18.
 */
@Slf4j
@Service
public class PositionCalculator {
    public Map<LocalDate, BigInteger> calculate(List<EthereumTransaction> transactionList) {

        Map<LocalDate, BigInteger> mapValuePerDay = transactionList.stream()
                .collect(Collectors.groupingBy(ethereumTransaction ->
                                DateTimeUtils.unixTimestampToLocalDate(ethereumTransaction.getTimeStamp()),
                        Collectors.reducing(BigInteger.ZERO,
                        EthereumTransaction::getValue,
                        BigInteger::add)));


        TreeMap<LocalDate, BigInteger> treeMap = new TreeMap<>(mapValuePerDay);
        treeMap.entrySet().stream().forEach(entry->{System.out.println(entry.getKey() + "=" + entry.getValue());});

        return mapValuePerDay;
    }

    public Map<LocalDate, List<EthereumTransaction>> getMapListDailyTransactions(
            List<EthereumTransaction> transactionList) {

        //Convert to map
        Map<LocalDate, List<EthereumTransaction>> mapDailyTx = transactionList.stream()
                .collect(Collectors.groupingBy(ethereumTransaction ->
                        DateTimeUtils.unixTimestampToLocalDate(ethereumTransaction.getTimeStamp())));

        return mapDailyTx;
    }

    public void generateFullReport(List<EthereumTransaction> transactionList,
                    String address, Map<LocalDate,
                    List<EthereumTransaction>> mapDailyTransactions){

        FullReport fullReport = new FullReport();

        LocalDate dateIterator = DateTimeUtils.unixTimestampToLocalDate(transactionList.get(0).getTimeStamp());
        LocalDate lastDate = DateTimeUtils.unixTimestampToLocalDate(transactionList.get(transactionList.size()-1).getTimeStamp());

        BigInteger total = new BigInteger("0");
        for(;!dateIterator.isAfter(lastDate); dateIterator = dateIterator.plusDays(1)){
            List<EthereumTransaction> listTransactions = mapDailyTransactions.get(dateIterator);

            BigInteger dayTotal = sumTransactions(transactionList, address);
            total = total.add(dayTotal);

            DailyReport dailyReport = new DailyReport();
            dailyReport.setTransactionList(listTransactions);
            dailyReport.setTotalBalance(total);
            dailyReport.setDayBalance(dayTotal);

            fullReport.putDailyReport(dateIterator, dailyReport);
        }
    }

    private BigInteger sumTransactions(List<EthereumTransaction> transactionList, String address){
        BigInteger total = transactionList.stream()
                .map(ethereumTransaction -> {
                    if (ethereumTransaction.getFrom().equals(address))
                        return ethereumTransaction.getValue().negate();
                    return ethereumTransaction.getValue();
                })
                .reduce(BigInteger.ZERO, BigInteger::add);

        return total;
    }
}
