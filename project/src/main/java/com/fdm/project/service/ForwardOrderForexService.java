package com.fdm.project.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fdm.project.model.Currency;
import com.fdm.project.model.ForwardOrderForex;
import com.fdm.project.model.User;
import com.fdm.project.model.Wallet;
import com.fdm.project.repo.CurrencyRepo;
import com.fdm.project.repo.ForwardOrderForexRepo;
import com.fdm.project.repo.UserRepo;
import com.fdm.project.repo.WalletRepo;

@Service
public class ForwardOrderForexService {
    @Autowired
    private ForwardOrderForexRepo forwardOrderForexRepo;

    @Autowired
    private CurrencyRepo currencyRepo;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private WalletRepo walletRepo;

    public List<ForwardOrderForex> findAllForwardOrderForex() {
        return forwardOrderForexRepo.findAll();
    }

    public List<ForwardOrderForex> findActiveForwardOrderForex() {
        return forwardOrderForexRepo.findByIsActive(true).get();
    }
    
    public List<ForwardOrderForex> getForwardOrderForexByUser(User user){
        return forwardOrderForexRepo.getByUser(user);
    }

    public void addNewForwardOrder(ForwardOrderForex newForwardOrder) {
	forwardOrderForexRepo.save(newForwardOrder);
    }

    public void updateForwardOrder(ForwardOrderForex forwardOrder) {
	forwardOrderForexRepo.save(forwardOrder);
    }

    public boolean displayForwardOrderList(User currentUser, Model model) {
	boolean emptyForwardOrderList = false;
	
	List<ForwardOrderForex> forwardOrderList = forwardOrderForexRepo.getByIsActiveTrueAndUserIsNot(currentUser);
	if (forwardOrderList.size()==0) {
	    emptyForwardOrderList = true;
	}
	model.addAttribute("forwardOrderList", forwardOrderList);
	return emptyForwardOrderList;
	
    }

    public ForwardOrderForex getForwardOrderById(int buyOrderId) {
	
	return forwardOrderForexRepo.getById(buyOrderId);
    }

    public String validateBuyOrder(User currentUser, ForwardOrderForex buyOrder, Model model, RedirectAttributes redirectAttr) {
	Wallet userBuyWallet = walletRepo.getByUserAndCurrency(currentUser, buyOrder.getBuyCurrency());
	double minimumWalletBalance = buyOrder.getBuyAmount() * 1.1;
	if (userBuyWallet == null || userBuyWallet.getWalletBalance() < minimumWalletBalance) {
	    redirectAttr.addFlashAttribute("insufficientFund", true);
	    return "redirect:/" + currentUser.getUsername() + "/forward";
	} else {
	    model.addAttribute("buyOrder", buyOrder);
	    return "forward_buyOrderConfirm";
	}
    }

    public boolean validateNewForwardOrder(ForwardOrderForex newForwardOrder, RedirectAttributes redirectAttrs) {
	User currentUser = newForwardOrder.getUser();
        Currency buyCurrency = newForwardOrder.getBuyCurrency();
        Wallet buyWallet = walletRepo.getByUserAndCurrency(currentUser, buyCurrency);
        	
	boolean isValid = true;
	
	if (buyCurrency.getCurrencyId() == newForwardOrder.getSellCurrency().getCurrencyId()) {
	    isValid = false;
	    redirectAttrs.addFlashAttribute("differentCurrencyRequired", true);
            return isValid;
	}
	
	if (newForwardOrder.getBuyAmount()<= 0 || newForwardOrder.getSellAmount()<=0) {
	    isValid = false;
            redirectAttrs.addFlashAttribute("zeroBuyAmount", true);
            return isValid;
	}
	
	if (buyWallet.getWalletBalance() < newForwardOrder.getBuyAmount()*0.1) {
	    isValid = false;
            redirectAttrs.addFlashAttribute("insufficientFundNew", true);
            return isValid;
	}
	
	return isValid;
    }

}