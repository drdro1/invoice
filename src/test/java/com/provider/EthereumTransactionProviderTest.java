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
    @Autowired private EthereumTransactionProvider ethereumTransactionProvider;

    @Test
    public void testInvalidAddress() throws Exception {
        EthereumTransactionsMessage ethtxmsg = ethereumTransactionProvider.getEtherscanTransactions("0xde0198e4fb0959fab7521b6899abce999fd7dcb");

        Assert.assertNotNull(ethtxmsg);

        List<EthereumTransaction> transactionList = ethtxmsg.getResult();

        Assert.assertNotNull(transactionList);
        Assert.assertFalse(transactionList.isEmpty());
    }

    @Test
    public void testGetEtherscanTransactions() throws Exception {
        EthereumTransactionsMessage ethtxmsg = ethereumTransactionProvider.getEtherscanTransactions("0xede0198e4fb0959fab7521b6899abce999fd7dcb");

        Assert.assertNotNull(ethtxmsg);

        List<EthereumTransaction> transactionList = ethtxmsg.getResult();

        Assert.assertNotNull(transactionList);
        Assert.assertFalse(transactionList.isEmpty());
    }

    @Test
    public void testCache() throws Exception {
        long millis = System.currentTimeMillis();
        EthereumTransactionsMessage ethTxMsg = ethereumTransactionProvider.getEtherscanTransactions("0xde0b295669a9fd93d5f28d9ec85e40f4cb697bae");
        log.info("Time={}", (System.currentTimeMillis()-millis));
        Thread.sleep(1000);

        millis = System.currentTimeMillis();
        ethTxMsg = ethereumTransactionProvider.getEtherscanTransactions("0xede0198e4fb0959fab7521b6899abce999fd7dcb");
        log.info("Time={}", (System.currentTimeMillis() - millis));
    }
}