package com.fdm.project.model.apimodel;

import java.util.Map;

public class CurrencyResponse {
    private Map<String, CurrencyRate> rates;

    public CurrencyResponse() {

    }

    public Map<String, CurrencyRate> getRates() {
        return rates;
    }

    public void setRates(Map<String, CurrencyRate> rates) {
        this.rates = rates;
    }

}
