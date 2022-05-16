package com.fdm.project.service;

import com.fdm.project.model.Currency;
import com.fdm.project.model.ForwardOrderForex;
import com.fdm.project.model.SpotOrderForex;
import com.fdm.project.model.User;
import com.fdm.project.repo.CurrencyRepo;
import com.fdm.project.repo.SpotOrderForexRepo;
import com.fdm.project.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class SpotOrderForexService {
    @Autowired
    private SpotOrderForexRepo spotOrderForexRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CurrencyRepo currencyRepo;

    public List<SpotOrderForex> findAllSpotOrderForex() {
        return spotOrderForexRepo.findAll();
    }

    public List<SpotOrderForex> findActiveSpotOrderForex() {
        return spotOrderForexRepo.findByIsActive(true).get();
    }

    public void fakeSpotOrder() {
        SpotOrderForex spotOrderForex = new SpotOrderForex();
        Currency currency = currencyRepo.findById(10).get();
        Currency currency1 = currencyRepo.findById(12).get();
        User user = userRepo.getById(1);
        spotOrderForex.setActive(true);
        spotOrderForex.setUser(user);
        spotOrderForex.setBuyAmount(500);
        spotOrderForex.setBuyCurrency(currency);
        spotOrderForex.setSellCurrency(currency1);
        spotOrderForex.setSellAmount(5200);
        java.util.Date date = new java.util.Date();
        long timeInMilliSeconds = date.getTime();
        java.sql.Date date1 = new java.sql.Date(timeInMilliSeconds);
        spotOrderForex.setExpiryDate(date1);
        spotOrderForexRepo.save(spotOrderForex);

    }

    public void dumSpotOrder() {
        SpotOrderForex spotOrderForex = new SpotOrderForex();
        Currency currency = currencyRepo.findById(10).get();
        Currency currency1 = currencyRepo.findById(12).get();
        User user = userRepo.getById(1);
        spotOrderForex.setActive(false);
        spotOrderForex.setUser(user);
        spotOrderForex.setBuyAmount(500);
        spotOrderForex.setBuyCurrency(currency);
        spotOrderForex.setSellCurrency(currency1);
        spotOrderForex.setSellAmount(5200);
        java.util.Date date = new java.util.Date();
        long timeInMilliSeconds = date.getTime();
        java.sql.Date date1 = new java.sql.Date(timeInMilliSeconds);
        spotOrderForex.setExpiryDate(date1);
        spotOrderForexRepo.save(spotOrderForex);

    }
}
