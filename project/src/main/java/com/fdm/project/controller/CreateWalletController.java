package com.fdm.project.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fdm.project.model.Currency;
import com.fdm.project.model.User;
import com.fdm.project.model.Wallet;
import com.fdm.project.service.CurrencyService;
import com.fdm.project.service.WalletService;

@Controller
public class CreateWalletController {   
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private WalletService walletService;
    
    @GetMapping("/{username}/wallet/createWallet")
    public String accessCreateWalletPage(@PathVariable String username, Model model) {
	ArrayList<Currency> currencyList = (ArrayList<Currency>) currencyService.getAllCurrencies();
	model.addAttribute("currencyList", currencyList);
	return "wallet_createWallet";
    }
    
    @PostMapping("/{username}/wallet/createWallet")
    public String addNewWallet(@PathVariable String username, @RequestParam String walletCurrencyCode) {
	Wallet newWallet = new Wallet();
	newWallet.setCurrency(currencyService.getByCurrencyCode(walletCurrencyCode));
	newWallet.setWalletBalance(0);
	walletService.addNewWallet(newWallet);
	return "redirect:/" + username + "/wallet";
    }
}
