package com.fdm.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdm.project.model.SpotOrderTransaction;
import com.fdm.project.repo.SpotOrderTransactionRepo;

@Service
public class SpotOrderTransactionService {
    @Autowired
    private SpotOrderTransactionRepo spotOrderTransactionRepo;
    
    public void addNewSpotOrderTransaction(SpotOrderTransaction newTransaction) {
	spotOrderTransactionRepo.save(newTransaction);
    }
    
    public void updateSpotOrderTransaction(SpotOrderTransaction transaction) {
	spotOrderTransactionRepo.save(transaction);
    }
}
