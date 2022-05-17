package com.fdm.project.service;

import com.fdm.project.model.Currency;
import com.fdm.project.model.SpotOrderTransaction;
import com.fdm.project.repo.SpotOrderTransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fdm.project.model.User;
import com.fdm.project.repo.UserRepo;

import java.util.List;

@Service
public class SpotTransactionService {

    @Autowired
    private SpotOrderTransactionRepo spotOrderTransactionRepo;

    public List<SpotOrderTransaction> getAllSpotOrderTransactionHistory() {
        return spotOrderTransactionRepo.findAll();
    }

}