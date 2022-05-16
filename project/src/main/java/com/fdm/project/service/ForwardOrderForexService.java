package com.fdm.project.service;

import com.fdm.project.model.Currency;
import com.fdm.project.model.ForwardOrderForex;
import com.fdm.project.model.SpotOrderForex;
import com.fdm.project.model.User;
import com.fdm.project.repo.CurrencyRepo;
import com.fdm.project.repo.ForwardOrderForexRepo;
import com.fdm.project.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ForwardOrderForexService {
    @Autowired
    private ForwardOrderForexRepo forwardOrderForexRepo;

    @Autowired
    private CurrencyRepo currencyRepo;

    @Autowired
    private UserRepo userRepo;


    public List<ForwardOrderForex> findAllForwardOrderForex() {
        return forwardOrderForexRepo.findAll();
    }

    public List<ForwardOrderForex> findActiveForwardOrderForex() {
        return forwardOrderForexRepo.findByIsActive(true).get();
    }


    public void fakeForwardOrder() {
        ForwardOrderForex forwardOrderForex = new ForwardOrderForex();
        Currency currency = currencyRepo.findById(10).get();
        Currency currency1 = currencyRepo.findById(12).get();
        User user = userRepo.getById(1);
        forwardOrderForex.setUser(user);
        forwardOrderForex.setActive(true);
        forwardOrderForex.setBuyAmount(500);
        forwardOrderForex.setSellAmount(560);
        forwardOrderForex.setBuyCurrency(currency);
        forwardOrderForex.setSellCurrency(currency1);
        java.util.Date date = new java.util.Date();
        long timeInMilliSeconds = date.getTime();
        java.sql.Date date1 = new java.sql.Date(timeInMilliSeconds);
        forwardOrderForex.setClosingDate(date1);
        forwardOrderForex.setExpiryDate(date1);
        forwardOrderForexRepo.save(forwardOrderForex);

    }

    public void dumForwardOrder() {
        ForwardOrderForex forwardOrderForex = new ForwardOrderForex();
        Currency currency = currencyRepo.findById(10).get();
        Currency currency1 = currencyRepo.findById(12).get();
        User user = userRepo.getById(1);
        forwardOrderForex.setUser(user);
        forwardOrderForex.setActive(false);
        forwardOrderForex.setBuyAmount(500);
        forwardOrderForex.setSellAmount(560);
        forwardOrderForex.setBuyCurrency(currency);
        forwardOrderForex.setSellCurrency(currency1);
        java.util.Date date = new java.util.Date();
        long timeInMilliSeconds = date.getTime();
        java.sql.Date date1 = new java.sql.Date(timeInMilliSeconds);
        forwardOrderForex.setClosingDate(date1);
        forwardOrderForex.setExpiryDate(date1);
        forwardOrderForexRepo.save(forwardOrderForex);
    }
}