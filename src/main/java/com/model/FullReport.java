package com.model;

import java.time.LocalDate;
import java.util.Map;

/**
 * Created by alejandrosantamaria on 10/06/18.
 */
public class FullReport {
    private Map<LocalDate, DailyReport> dailyReportMap;


    public void putDailyReport(LocalDate localDate, DailyReport dailyReport){
        dailyReportMap.put(localDate, dailyReport);
    }

    public double getVaultFees(LocalDate dateStart, LocalDate dateEnd){
        double toRet = 0;
        for(LocalDate dateIterator = dateStart; dateIterator.isAfter(dateEnd); dateIterator = dateIterator.plusDays(1)){
            DailyReport dailyReport = dailyReportMap.get(dateIterator);

        }

        return toRet;
    }
}
