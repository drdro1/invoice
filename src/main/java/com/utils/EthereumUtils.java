package com.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * Created by alejandrosantamaria on 12/06/18.
 */
public class EthereumUtils {
    public final static BigInteger WEI_FACTOR = new BigInteger("1000000000000000000");
    public final static int ETH_DECIMALS = 9;

    public static boolean isValidAddress(String addr) {
        String regex = "^0x[0-9a-f]{40}$";

        if (addr.matches(regex)) {
            return true;
        }

        return false;
    }

    public static double getEthereumFromWei(BigInteger wei){
        BigDecimal etherBD = new BigDecimal(wei)
                .divide(new BigDecimal(WEI_FACTOR), ETH_DECIMALS, RoundingMode.HALF_EVEN);

        return etherBD.doubleValue();
    }
}
