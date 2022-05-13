package com.fdm.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdm.project.model.Wallet;
import com.fdm.project.repo.WalletRepo;

@Service
public class WalletService {
    @Autowired
    private WalletRepo walletRepo;
    
    public void addNewWallet(Wallet newWallet) {
	walletRepo.save(newWallet);
    }
}
