package com.fdm.project.service;

import com.fdm.project.model.User;
import com.fdm.project.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdm.project.model.SpotOrderTransaction;
import com.fdm.project.repo.SpotOrderTransactionRepo;

import java.util.List;

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

    public List<SpotOrderTransaction> getSpotOrderTransactionByUser(User user) {
        return spotOrderTransactionRepo.getByUser1(user);
    }
}
