package com.fdm.project.service;


import com.fdm.project.model.Currency;
import com.fdm.project.model.SpotOrderForex;
import com.fdm.project.model.User;
import com.fdm.project.model.Wallet;
import com.fdm.project.repo.CurrencyRepo;
import com.fdm.project.repo.SpotOrderForexRepo;
import com.fdm.project.repo.UserRepo;
import com.fdm.project.repo.WalletRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.util.List;

@Service
public class SpotOrderForexService {
    @Autowired
    private SpotOrderForexRepo spotOrderForexRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CurrencyRepo currencyRepo;

    public List<SpotOrderForex> findAllSpotOrderForex() {
        return spotOrderForexRepo.findAll();
    }


    @Autowired
    private WalletRepo walletRepo;

    public void addNewSpotOrder(SpotOrderForex order) {
        spotOrderForexRepo.save(order);
    }

    public void updateSpotOrder(SpotOrderForex order) {
        spotOrderForexRepo.save(order);
    }

    public List<SpotOrderForex> findActiveMarketSpotOrderForex() {
        return spotOrderForexRepo.findByIsActiveAndSellAmount(true, 0).get();
    }

    public List<SpotOrderForex> findActiveLimitSpotOrderForex() {
        return spotOrderForexRepo.findByIsActiveAndSellAmountIsNot(true, 0).get();
    }


    public boolean displayLimitOrderList(User currentUser, Model model) {
        boolean emptyLimitOrderList = true;

        List<SpotOrderForex> limitOrderList = spotOrderForexRepo.getLimitOrdersListNotByUser(currentUser);
        if (limitOrderList.size() > 0) {
            emptyLimitOrderList = false;
        }

        model.addAttribute("limitOrderList", limitOrderList);

        return emptyLimitOrderList;
    }

    public boolean displayMarketOrderList(User currentUser, Model model) {
        boolean emptyMarketOrderList = true;

        List<SpotOrderForex> marketOrderList = spotOrderForexRepo.getMarketOrdersListNotByUser(currentUser);
        if (marketOrderList.size() > 0) {
            emptyMarketOrderList = false;
        }

        model.addAttribute("marketOrderList", marketOrderList);

        return emptyMarketOrderList;
    }

    public boolean isValidSpotOrder(SpotOrderForex newOrder, RedirectAttributes redirectAttrs) {
        User currentUser = newOrder.getUser();
        Currency buyCurrency = newOrder.getBuyCurrency();
        Currency sellCurrency = newOrder.getSellCurrency();
        Wallet sellWallet = walletRepo.getByUserAndCurrency(currentUser, sellCurrency);

        boolean isValid = true;
        boolean isLimitOrder = (newOrder.getSellAmount() > 0 && newOrder.isActive());
        // For market orders:
        // required balance in wallet must be no less than marketOrderThreshold current value of buy amount
        double marketOrderThreshold = 1.2;
        double requiredWalletBalance = marketOrderThreshold * newOrder.getBuyAmount() / buyCurrency.getCurrencyRate() * sellCurrency.getCurrencyRate();

        if (newOrder.getBuyCurrency().getCurrencyId() == newOrder.getSellCurrency().getCurrencyId()) {
            isValid = false;
            redirectAttrs.addFlashAttribute("differentCurrencyRequired", true);
            return isValid;
        }

        if (newOrder.getBuyAmount() <= 0 || newOrder.getSellAmount()<0) {
            isValid = false;
            redirectAttrs.addFlashAttribute("zeroBuyAmount", true);
            return isValid;
        }

        if (newOrder.getSellAmount() > 0) {
            double currentRate = newOrder.getSellAmount() / newOrder.getBuyAmount();
            double marketRate = newOrder.getSellCurrency().getCurrencyRate() / newOrder.getBuyCurrency().getCurrencyRate();
            // if input exchange rate exceeds market exchange rate by 25%
            if (Math.abs((marketRate - currentRate) * 100 / marketRate) > 25) {
                isValid = false;
                redirectAttrs.addFlashAttribute("invalidExcahngeRate", true);
                return isValid;
            }
        }
        
        java.util.Date date = new java.util.Date();
        long timeInMilliSeconds = date.getTime();
        java.sql.Date curdate = new java.sql.Date(timeInMilliSeconds);
        if (newOrder.getExpiryDate().before(curdate)) {
            isValid = false;
            redirectAttrs.addFlashAttribute("outDatedOrder", true);
            return isValid;
        }

        if (isLimitOrder) {
            isValid = (sellWallet.getWalletBalance() >= newOrder.getSellAmount());
        } else {
            isValid = (sellWallet.getWalletBalance() >= requiredWalletBalance);
        }

        if (isValid) {
            redirectAttrs.addFlashAttribute("orderCreated", true);
        } else {
            redirectAttrs.addFlashAttribute("insufficientFund", true);
        }
        
        return isValid;
    }

	public List<SpotOrderForex> getCurrentSpotOrderByUser(User user){
		return spotOrderForexRepo.getByUser(user);
	}
}
