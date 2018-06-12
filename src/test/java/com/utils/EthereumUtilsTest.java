package com.utils;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

/**
 * Created by alejandrosantamaria on 12/06/18.
 */
public class EthereumUtilsTest extends TestCase{

    @Test
    public void testIsValidAddress(){
        Assert.assertTrue(EthereumUtils.isValidAddress("0xd654bDD32FC99471455e86C2E7f7D7b6437e9179".toLowerCase()));
        Assert.assertFalse(EthereumUtils.isValidAddress("0xh654bDD32FC99471455e86C2E7f7D7b6437e9179".toLowerCase()));
        Assert.assertFalse(EthereumUtils.isValidAddress("0xh654dbDD32FC99471455e86C2E7f7D7b6437e9179".toLowerCase()));
        Assert.assertFalse(EthereumUtils.isValidAddress("0xh654dbDD32FC99471455e86C2E7f7D7b7e9179".toLowerCase()));
    }

    @Test
    public void testGetEthereumFromWei(){
        double precision = 1/(10^EthereumUtils.ETH_DECIMALS);

        double ethereum = EthereumUtils.getEthereumFromWei(new BigInteger("179964050000000000"));
        Assert.assertEquals(ethereum, 0.17996405, precision);

        ethereum = EthereumUtils.getEthereumFromWei(new BigInteger("123456789123456789"));
        Assert.assertEquals(ethereum, 0.123456789, precision);
    }

}
