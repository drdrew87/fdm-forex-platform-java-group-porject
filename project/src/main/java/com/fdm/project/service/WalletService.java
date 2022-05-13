package com.fdm.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdm.project.model.Currency;
import com.fdm.project.model.User;
import com.fdm.project.model.Wallet;
import com.fdm.project.repo.CurrencyRepo;
import com.fdm.project.repo.UserRepo;
import com.fdm.project.repo.WalletRepo;

@Service
public class WalletService {
    @Autowired
    private WalletRepo walletRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CurrencyRepo currencyRepo;
    
    public void addNewWallet(Wallet newWallet) {
	walletRepo.save(newWallet);
    }

    public boolean validateNewWallet(String username, String newWalletCurrencyCode) {
	User user = userRepo.getByUsername(username);
	Currency currency = currencyRepo.getByCurrencyCode(newWalletCurrencyCode);
	Wallet existingWallet = walletRepo.getByUserAndCurrency(user, currency);
	return (existingWallet==null);
    }
    
    
}
