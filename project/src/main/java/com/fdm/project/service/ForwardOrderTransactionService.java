package com.fdm.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdm.project.model.ForwardOrderTransaction;
import com.fdm.project.repo.ForwardOrderTransactionRepo;

@Service
public class ForwardOrderTransactionService {
    @Autowired
    private ForwardOrderTransactionRepo transactionRepo;
    
    public void addNewForwardTransaction(ForwardOrderTransaction newTransaction) {
	transactionRepo.save(newTransaction);
    }
    
    public void updateForwardTransaction(ForwardOrderTransaction newTransaction) {
	transactionRepo.save(newTransaction);
    }

}
