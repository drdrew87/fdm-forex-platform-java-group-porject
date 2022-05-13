package com.fdm.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdm.project.model.Bank;
import com.fdm.project.repo.BankRepo;

@Service
public class BankService {
    @Autowired
    private BankRepo bankRepo;
    
    public void addNewBankAccount(Bank newBankAccount) {
	bankRepo.save(newBankAccount);
    }

}