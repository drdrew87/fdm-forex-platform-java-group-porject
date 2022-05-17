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

    public List<SpotOrderForex> findActiveMarketSpotOrderForex() {
        return spotOrderForexRepo.findByIsActiveAndSellAmount(true,0).get();
    }

    public List<SpotOrderForex> findActiveLimitSpotOrderForex() {
        return spotOrderForexRepo.findByIsActiveAndSellAmountIsNot(true,0).get();
    }


}
