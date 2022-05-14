package com.fdm.project.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fdm.project.model.Bank;
import com.fdm.project.model.User;
import com.fdm.project.repo.BankRepo;

@Service
public class BankService {
    @Autowired
    private BankRepo bankRepo;
    
    public void addNewBankAccount(Bank newBankAccount) {
	bankRepo.save(newBankAccount);
    }
    
    public void updateAccount(Bank selectedBank) {
	bankRepo.save(selectedBank);	
    }

    public List<Bank> getBankByUser(User user) {
	return bankRepo.getByUser(user);
    }

    public Bank getBankById(int bankId) {
	return bankRepo.getById(bankId);
    }
    
    public void displayBankListInDropdown(User currentUser, Model model) {
	List<Bank> bankList = bankRepo.getByUser(currentUser);
	model.addAttribute("bankList", bankList);
	if (bankList.size()==0) {
	    model.addAttribute("emptyBankList", true);
	}
    }
    
    public boolean selectBank(HttpSession session, Model model) {
	boolean isBankSelected = false;
	long selectedBankAccountNumber = 0;
	String selectedBankCurrency = null;
	if (session.getAttribute("selectedBankId") != null) {
	    int selectedBankId = Integer.valueOf((String) session.getAttribute("selectedBankId"));
	    Bank selectedBank = bankRepo.getById(selectedBankId);
	    selectedBankCurrency = selectedBank.getCurrency().getCurrencyCode();
	    selectedBankAccountNumber = selectedBank.getBankAccountNumber();
	    isBankSelected = true;
	    model.addAttribute("selectedBankAccountNumber",selectedBankAccountNumber);
	    model.addAttribute("selectedBankCurrency",selectedBankCurrency);
	    model.addAttribute("isBankSelected", true);
	}
	return isBankSelected;
    }





}
