package com.provider;

import com.model.ethereum.EthereumTransaction;
import com.model.ethereum.EthereumTransactionsMessage;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by alejandrosantamaria on 10/06/18.
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EthereumTransactionProviderTest extends TestCase {
    private static String testAddressFewTx = "0x98e4ea439617ddd70ca66d41a543fefd931ebee0";
    private final static String testAddressManyTx = "0xde0b295669a9fd93d5f28d9ec85e40f4cb697bae";
    private final static String testInvalidAddress = "0xde0b295669a9fd93d5f28d9ec85e40f4c";

    @Autowired private EthereumTransactionProvider ethereumTransactionProvider;

    @Test
    public void testInvalidAddress() throws Exception {
        EthereumTransactionsMessage ethtxmsg = ethereumTransactionProvider.getEtherscanTransactions(testInvalidAddress);

        Assert.assertNotNull(ethtxmsg);

        List<EthereumTransaction> transactionList = ethtxmsg.getResult();

        Assert.assertTrue(transactionList.isEmpty());
    }

    @Test
    public void testGetEtherscanTransactions() throws Exception {
        EthereumTransactionsMessage ethtxmsg = ethereumTransactionProvider.getEtherscanTransactions(testAddressFewTx);

        Assert.assertNotNull(ethtxmsg);

        List<EthereumTransaction> transactionList = ethtxmsg.getResult();

        Assert.assertNotNull(transactionList);
        Assert.assertFalse(transactionList.isEmpty());
    }

    @Test
    public void testCache() throws Exception {
        long millis = System.currentTimeMillis();
        EthereumTransactionsMessage ethTxMsg = ethereumTransactionProvider.getEtherscanTransactions(testAddressManyTx);
        long timeNoCache = System.currentTimeMillis()-millis;
        log.info("TimeNoCache={}", (timeNoCache));
        Thread.sleep(1000);

        millis = System.currentTimeMillis();
        ethTxMsg = ethereumTransactionProvider.getEtherscanTransactions(testAddressManyTx);
        long timeWithCache = System.currentTimeMillis()-millis;
        log.info("TimeWithCache={}", timeWithCache);

        Assert.assertTrue(timeNoCache > (10 * timeWithCache));
    }
}