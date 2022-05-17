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



}
