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
import com.fdm.project.model.Wallet;
import com.fdm.project.service.CurrencyService;

@Controller
public class CreateWalletController {   
    @Autowired
    private CurrencyService currencyService;
    
    @GetMapping("/{username}/wallet/createWallet")
    public String accessCreateWalletPage(@PathVariable String username, Model model) {
	ArrayList<Currency> currencyList = (ArrayList<Currency>) currencyService.findAllCurrency();
	model.addAttribute("currencyList", currencyList);
	return "wallet_createWallet";
    }
    
    @PostMapping("/{username}/wallet/createWallet")
    public String addNewWallet(@PathVariable String username, @RequestParam String walletCurrencyId) {
	Wallet newWallet = new Wallet();
	newWallet.setCurrency(currencyService.findByCurrencyId(Integer.valueOf(walletCurrencyId)));
	newWallet.setWalletBalance(0);
	return "redirect:/" + username + "/wallet";
    }
}
