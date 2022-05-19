package com.fdm.project.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fdm.project.model.Bank;
import com.fdm.project.model.Currency;
import com.fdm.project.model.Wallet;
import com.fdm.project.service.BankService;
import com.fdm.project.service.CurrencyService;
import com.fdm.project.service.UserService;
import com.fdm.project.service.WalletService;

@Controller
public class RegisterBankAccountController {
    @Autowired 
    private BankService bankService;
    @Autowired
    private WalletService walletService;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private UserService userService;
    
    
    @GetMapping("/{username}/wallet/registerBankAccount")
    public String accessRegisterBankAccountPage(@PathVariable String username, Model model, HttpServletRequest request) {
	HttpSession session = request.getSession();
	if (!userService.verifyLogin(username, session)) {
	    return "redirect:/login";
	}
	ArrayList<Currency> currencyList = (ArrayList<Currency>) currencyService.getAllCurrencies();
	model.addAttribute("currencyList", currencyList);
	return "wallet_registerBankAccount";
    }
    
    @PostMapping("/{username}/wallet/registerBankAccount")
    public String registerNewBankAccount(@PathVariable String username, @RequestParam String bankAccountNumber, @RequestParam String walletCurrencyCode) {
	
	Bank newBankAccount = new Bank();
	newBankAccount.setBankAccountNumber(Long.valueOf(bankAccountNumber));
	newBankAccount.setCurrency(currencyService.getByCurrencyCode(walletCurrencyCode));
	newBankAccount.setUser(userService.getUserByUsername(username));
	newBankAccount.setBalance(1_000_000_000);
	bankService.addNewBankAccount(newBankAccount);
	
	if (walletService.validateNewWallet(username, walletCurrencyCode)) {
	    Wallet newWallet = new Wallet();
	    newWallet.setUser(userService.getUserByUsername(username));
	    newWallet.setCurrency(currencyService.getByCurrencyCode(walletCurrencyCode));
	    newWallet.setWalletBalance(0);
	    walletService.addNewWallet(newWallet);
	}
	
	return "redirect:/" + username + "/wallet";
    }
    
    
}
