package com.processor;

import com.model.report.DailyReport;
import com.model.ethereum.EthereumTransaction;
import com.model.ethereum.EthereumTransactionsMessage;
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
    private static String testAddress = "0x98e4ea439617ddd70ca66d41a543fefd931ebee0";

    @Autowired EthereumTransactionProvider ethereumTransactionProvider;
    @Autowired PositionCalculator positionCalculator;


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