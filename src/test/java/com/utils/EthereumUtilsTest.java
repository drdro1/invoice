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
        Assert.assertTrue(EthereumUtils.isValidAddress("0x98e4ea439617ddd70ca66d41a543fefd931ebee0"));
        Assert.assertFalse(EthereumUtils.isValidAddress("0x98h4ea439617ddd70ca66d41a543fefd931ebee0"));
        Assert.assertFalse(EthereumUtils.isValidAddress("0x8e4ea439617ddd70ca66d41a543fefd931ebee0"));
        Assert.assertFalse(EthereumUtils.isValidAddress("0x198e4ea439617ddd70ca66d41a543fefd931ebee0"));
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
