package com.processor;

import com.model.DailyReport;
import com.model.Ethereum.EthereumTransaction;
import com.model.Ethereum.EthereumTransactionsMessage;
import com.provider.EthereumTransactionProvider;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Created by alejandrosantamaria on 11/06/18.
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PositionCalculatorTest extends TestCase {

//    private static String testAddress = "0xde0b295669a9fd93d5f28d9ec85e40f4cb697bae";
    private static String testAddress = "0x98E4EA439617DDD70cA66D41A543FEFD931EBEe0".toLowerCase();

    @Autowired EthereumTransactionProvider ethereumTransactionProvider;
    @Autowired PositionCalculator positionCalculator;

    public void testCalculate() throws Exception {

    }

    @Test
    public void testGetMapListDailyTransactions() throws Exception {
        EthereumTransactionsMessage ethTxMsg = ethereumTransactionProvider.getEtherscanTransactions(testAddress);
        Map<LocalDate, List<EthereumTransaction>> mapListDailyTransactions =
                positionCalculator.getMapListDailyTransactions(ethTxMsg.getResult());

        Assert.assertTrue(mapListDailyTransactions.size() > 0);
    }

    @Test
    public void testGenerateFullReport() throws Exception {
        EthereumTransactionsMessage ethTxMsg = ethereumTransactionProvider.getEtherscanTransactions(testAddress);
        List<EthereumTransaction> transactionList = ethTxMsg.getResult();

        Map<LocalDate, List<EthereumTransaction>> mapListDailyTransactions =
                positionCalculator.getMapListDailyTransactions(transactionList);

        Map<LocalDate, DailyReport> dailyReportMap = positionCalculator.generateFullReport(
                transactionList, testAddress, mapListDailyTransactions);
    }
}