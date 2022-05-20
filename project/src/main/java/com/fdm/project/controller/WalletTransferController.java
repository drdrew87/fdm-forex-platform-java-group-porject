package com.fdm.project.controller;

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
    public String accessWalletTransferPage(@PathVariable String username, HttpServletRequest request, Model model) {
	User currentUser = userService.getUserByUsername(username);
	HttpSession session = request.getSession();
	if (!userService.verifyLogin(username, session)) {
	    return "redirect:/login";
	}
	bankService.displayBankListInDropdown(currentUser, model);
	
	boolean isBankSelected = bankService.selectBank(session, model);
	
	walletService.displayWalletListInDropdown(currentUser, model);

	boolean isWalletSelected = walletService.selectWallet(session, model);
	
	double exchangeRate = 0;
	if (isBankSelected && isWalletSelected) {
	    int selectedBankId = Integer.valueOf((String) session.getAttribute("selectedBankId"));
	    Bank selectedBank = bankService.getBankById(selectedBankId);
	    int selectedWalletId = Integer.valueOf((String) session.getAttribute("selectedWalletId"));
	    Wallet selectedWallet = walletService.getWalletById(selectedWalletId);
	    exchangeRate = selectedWallet.getCurrency().getCurrencyRate() / selectedBank.getCurrency().getCurrencyRate();
	}
	model.addAttribute("exchangeRate", exchangeRate);
	
	return "wallet_transfer";
    }
    
    @PostMapping("/{username}/wallet/transfer/bankSelection")
    public String selectBankForTransfer(@PathVariable String username, @RequestParam String selectedBankId, HttpServletRequest request) {
	HttpSession session = request.getSession();
	session.setAttribute("selectedBankId", selectedBankId);
	return "redirect:/" + username + "/wallet/transfer";
    }
    
    @PostMapping("/{username}/wallet/transfer/removeBankSelection")
    public String removeBankSelection(@PathVariable String username, HttpServletRequest request) {
	HttpSession session = request.getSession();
	session.removeAttribute("selectedBankId");
	return "redirect:/" + username + "/wallet/transfer";
    }
    
    @PostMapping("/{username}/wallet/transfer/walletSelection")
    public String selectWalletForTransfer(@PathVariable String username, @RequestParam String selectedWalletId, HttpServletRequest request) {
	HttpSession session = request.getSession();
	session.setAttribute("selectedWalletId", selectedWalletId);
	return "redirect:/" + username + "/wallet/transfer";
    }
    
    @PostMapping("/{username}/wallet/transfer/removeWalletSelection")
    public String removeWalletSelection(@PathVariable String username, HttpServletRequest request) {
	HttpSession session = request.getSession();
	session.removeAttribute("selectedWalletId");
	return "redirect:/" + username + "/wallet/transfer";
    }
    
    @PostMapping("/{username}/wallet/transfer/confirmTransfer")
    public String confirmTransfer(@PathVariable String username, @RequestParam String transferAmount, HttpServletRequest request) {
	HttpSession session = request.getSession();
	
	int selectedBankId = Integer.valueOf((String) session.getAttribute("selectedBankId"));
	Bank selectedBank = bankService.getBankById(selectedBankId);
	int selectedWalletId = Integer.valueOf((String) session.getAttribute("selectedWalletId"));
	Wallet selectedWallet = walletService.getWalletById(selectedWalletId);
	
	double amountReceive = Double.valueOf(transferAmount);
	double amountRemit = amountReceive / selectedWallet.getCurrency().getCurrencyRate() * selectedBank.getCurrency().getCurrencyRate(); 
	selectedBank.setBalance(selectedBank.getBalance()-amountRemit);
	selectedWallet.setWalletBalance(selectedWallet.getWalletBalance()+amountReceive);
	bankService.updateAccount(selectedBank);
	walletService.updateWallet(selectedWallet);
	session.removeAttribute("selectedBankId");
	session.removeAttribute("selectedWalletId");
	return "redirect:/" + username + "/wallet";
    }
}
