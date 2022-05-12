package com.fdm.project.service;

import com.fdm.project.model.apimodel.CurrencyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

public class CurrencyRateService {

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

    @Service
    @EnableScheduling
    public class CurrencyRatesService {
        @Autowired
        private CurrencyRatesClient currencyRatesClient;
        private final long SECOND = 1000;
        private final long MINUTE = SECOND * 60;
        private final long HOUR = MINUTE * 60;

        public CurrencyResponse getCurrencyResponse () {
            return currencyRatesClient.getCurrencyRates();
        }
}
