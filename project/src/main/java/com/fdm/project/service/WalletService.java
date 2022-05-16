package com.fdm.project.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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
    
    public void updateWallet(Wallet selectedWallet) {
	walletRepo.save(selectedWallet);
    }

    public boolean validateNewWallet(String username, String newWalletCurrencyCode) {
	User user = userRepo.getByUsername(username);
	Currency currency = currencyRepo.getByCurrencyCode(newWalletCurrencyCode);
	Wallet existingWallet = walletRepo.getByUserAndCurrency(user, currency);
	return (existingWallet==null);
    }
    
    public List<Wallet> getWalletByUser(User user) {
	return walletRepo.getByUser(user);
    }

    public Double totalValue(User user) {
	ArrayList<Wallet> wallets = (ArrayList<Wallet>) walletRepo.getByUser(user);
	double preferredRate = 0;
	if (user.getPreferredCurrency()!=null) {
	    preferredRate = user.getPreferredCurrency().getCurrencyRate();
	} else {
	    preferredRate = currencyRepo.getByCurrencyCode("usd").getCurrencyRate();
	}
	
	double accountTotal = 0;
	for (Wallet wallet:wallets) {
	    accountTotal += wallet.getWalletBalance() / wallet.getCurrency().getCurrencyRate() * preferredRate;
	}
	
	return Math.round(accountTotal * 100.00) / 100.00;
    }

    public Wallet getWalletById(int walletId) {
	return walletRepo.getById(walletId);
    }

    public void displayWalletListInDropdown(User currentUser, Model model) {
	List<Wallet> walletList = walletRepo.getByUser(currentUser);
	model.addAttribute("walletList", walletList);
	if (walletList.size()==0) {
	    model.addAttribute("emptyWalletList", true);
	}
    }

    public boolean selectWallet(HttpSession session, Model model) {
	boolean isWalletSelected = false;
	String selectedWalletCurrency = null;
	if (session.getAttribute("selectedWalletId") != null) {
	    int selectedWalletId = Integer.valueOf((String) session.getAttribute("selectedWalletId"));
	    Wallet selectedWallet = walletRepo.getById(selectedWalletId);
	    selectedWalletCurrency = selectedWallet.getCurrency().getCurrencyCode();
	    isWalletSelected = true;
	    model.addAttribute("selectedWalletCurrency",selectedWalletCurrency);
	    model.addAttribute("isWalletSelected", true);
	}
	return isWalletSelected;
    }

    
}
