package com.fdm.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fdm.project.model.Bank;
import com.fdm.project.model.User;
import com.fdm.project.model.Wallet;
import com.fdm.project.service.BankService;
import com.fdm.project.service.UserService;
import com.fdm.project.service.WalletService;

@Controller
public class WalletTransferController {
    @Autowired
    private UserService userService;
    @Autowired
    private BankService bankService;
    @Autowired
    private WalletService walletService;
    
    @GetMapping("/{username}/wallet/transfer")
    public String accessWalletTransferPage(@PathVariable String username, Model model) {
	User currentUser = userService.getUserByUsername(username);
	List<Bank> bankList = bankService.getBankByUser(currentUser);
	model.addAttribute("bankList", bankList);
	if (bankList.size()==0) {
	    model.addAttribute("emptyBankList", true);
	}
	
	List<Wallet> walletList = walletService.getWalletByUser(currentUser);
	model.addAttribute("walletList", walletList);
	if (walletList.size()==0) {
	    model.addAttribute("emptyWalletList", true);
	}
	
	return "wallet_transfer";
    }
}
