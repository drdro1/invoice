package com.processor;

import com.model.DailyReport;

import java.time.LocalDate;
import java.util.Map;

/**
 * Created by alejandrosantamaria on 11/06/18.
 */
public class VaultInvoicer {

    public double getVaultFees(Map<LocalDate, DailyReport> dailyReportMap, LocalDate dateStart, LocalDate dateEnd){
        double toRet = 0;
        for(LocalDate dateIterator = dateStart; dateIterator.isAfter(dateEnd); dateIterator = dateIterator.plusDays(1)){
            DailyReport dailyReport = dailyReportMap.get(dateIterator);

        }

        return toRet;
    }
}
