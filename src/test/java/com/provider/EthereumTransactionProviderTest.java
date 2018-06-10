package com.provider;

import com.model.Ethereum.EthereumTransaction;
import com.model.Ethereum.EthereumTransactionsMessage;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by alejandrosantamaria on 10/06/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EthereumTransactionProviderTest extends TestCase {

    @InjectMocks
    private EthereumTransactionProvider ethereumTransactionProvider;

    @Test
    public void testGetEtherscanTransactions() throws Exception {
        EthereumTransactionsMessage ethtxmsg = ethereumTransactionProvider.getEtherscanTransactions("0xede0198e4fb0959fab7521b6899abce999fd7dcb");

        Assert.assertNotNull(ethtxmsg);

        List<EthereumTransaction> transactionList = ethtxmsg.getResult();
        Assert.assertNotNull(transactionList);
        Assert.assertFalse(transactionList.isEmpty());
    }
}