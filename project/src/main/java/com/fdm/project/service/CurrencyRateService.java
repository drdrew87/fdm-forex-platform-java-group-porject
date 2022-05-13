package com.fdm.project.service;

import com.fdm.project.model.Currency;
import com.fdm.project.model.apimodel.CurrencyRate;
import com.fdm.project.model.apimodel.CurrencyResponse;
import com.fdm.project.repo.CurrencyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.Map;

@Service
@EnableScheduling
public class CurrencyRateService {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CurrencyRepo currencyRepo;

    public CurrencyResponse getCurrencyRates() {
        CurrencyResponse currencyResponse = restTemplate.getForObject("https://api.coingecko.com/api/v3/exchange_rates", CurrencyResponse.class);

        return currencyResponse;
    }

    @Scheduled(fixedRate = 1000 * 10)
    public void updateCurrencyRates() {
        CurrencyResponse currencyResponse = restTemplate.getForObject("https://api.coingecko.com/api/v3/exchange_rates", CurrencyResponse.class);
        Map<String, CurrencyRate> currencyRate = currencyResponse.getRates();
        System.out.println(currencyRate.values());
        for (Map.Entry<String, CurrencyRate> entry : currencyRate.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            if (currencyRepo.findByCurrencyCode(entry.getKey()).isEmpty()) {
                Currency tempcurrency = new Currency();
                tempcurrency.setCurrencyName(entry.getValue().getName());
                tempcurrency.setCurrencyCode(entry.getKey());
                tempcurrency.setCurrencyRate(entry.getValue().getValue());
                Long datetime = System.currentTimeMillis();
                tempcurrency.setLastUpdateTime(new Timestamp(datetime));
                currencyRepo.save(tempcurrency);
            } else {
                System.out.println("updatin");
                Currency tempcurrency = currencyRepo.findByCurrencyCode(entry.getKey()).get();
                tempcurrency.setCurrencyRate(entry.getValue().getValue());
                Long datetime = System.currentTimeMillis();
                tempcurrency.setLastUpdateTime(new Timestamp(datetime));
                currencyRepo.save(tempcurrency);
            }


        }

    }

}