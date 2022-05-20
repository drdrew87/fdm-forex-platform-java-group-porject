//package com.fdm.project.client;
//
//import com.fdm.project.model.Currency;
//import com.fdm.project.model.apimodel.CurrencyRate;
//import com.fdm.project.model.apimodel.CurrencyResponse;
//import com.fdm.project.repo.CurrencyRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//import java.sql.Timestamp;
//import java.util.Map;
//
//@Component
//public class CurrencyRatesClinet {
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @Autowired
//    private CurrencyRepo currencyRepo;
//
//    public CurrencyResponse getCurrencyRates(){
//        CurrencyResponse currencyResponse = restTemplate.getForObject("https://api.coingecko.com/api/v3/exchange_rates",CurrencyResponse.class);
//
//        return currencyResponse;
//    }
//
//    }
//}
