package com.fdm.project.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fdm.project.model.Currency;
import com.fdm.project.model.SpotOrderForex;
import com.fdm.project.model.SpotOrderTransaction;
import com.fdm.project.model.User;
import com.fdm.project.model.Wallet;
import com.fdm.project.repo.CurrencyRepo;
import com.fdm.project.repo.SpotOrderForexRepo;
import com.fdm.project.repo.SpotOrderTransactionRepo;
import com.fdm.project.repo.UserRepo;
import com.fdm.project.repo.WalletRepo;

@Service
public class SpotOrderMatchingService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private WalletRepo walletRepo;
    @Autowired
    private CurrencyRepo currencyRepo;
    @Autowired
    private SpotOrderForexRepo spotOrderForexRepo;
    @Autowired
    private SpotOrderTransactionRepo spotOrderTransactionRepo;

    public void matchSpotOrder(SpotOrderForex newOrder, RedirectAttributes redirectAttrs) {
	// orderStatus: 0 = order fully filled, 1 = order partially filled, -1 = order
	// not filled
	int orderStatus = -1;

	boolean isLimitOrder = (newOrder.isActive() && newOrder.getSellAmount() > 0);
	User currentUser = newOrder.getUser();
	Currency buyCurrency = newOrder.getBuyCurrency();
	Currency sellCurrency = newOrder.getSellCurrency();
	double newOrderRate = newOrder.getBuyAmount() / newOrder.getSellAmount();

	List<SpotOrderForex> limitOrderMatchingList = spotOrderForexRepo.getLimitOrderMatchingList(currentUser,
		buyCurrency, sellCurrency);
	List<SpotOrderForex> marketOrderMatchingList = spotOrderForexRepo.getMarketOrderMatchingList(currentUser,
		buyCurrency, sellCurrency);

	// Limit Order
	if (isLimitOrder) {

	    Wallet user1BuyWallet = walletRepo.getByUserAndCurrency(currentUser, buyCurrency);
	    if (user1BuyWallet == null) {
		user1BuyWallet = new Wallet();
		user1BuyWallet.setUser(currentUser);
		user1BuyWallet.setCurrency(buyCurrency);
		user1BuyWallet.setWalletBalance(0);
		walletRepo.save(user1BuyWallet);
	    }
	    Wallet user1SellWallet = walletRepo.getByUserAndCurrency(currentUser, sellCurrency);

	    for (int i = 0; i < limitOrderMatchingList.size(); i++) {

		SpotOrderForex matchingOrder = limitOrderMatchingList.get(i);
		double matchingOrderRate = matchingOrder.getSellAmount() / matchingOrder.getBuyAmount();

		if (matchingOrderRate >= newOrderRate) {

		    Wallet user2BuyWallet = walletRepo.getByUserAndCurrency(matchingOrder.getUser(),
			    matchingOrder.getBuyCurrency());
		    if (user2BuyWallet == null) {
			user2BuyWallet = new Wallet();
			user2BuyWallet.setUser(matchingOrder.getUser());
			user2BuyWallet.setCurrency(matchingOrder.getBuyCurrency());
			user2BuyWallet.setWalletBalance(0);
			walletRepo.save(user2BuyWallet);
		    }
		    Wallet user2SellWallet = walletRepo.getByUserAndCurrency(matchingOrder.getUser(),
			    matchingOrder.getSellCurrency());

		    double newOrderSetteledBuyAmount = 0;
		    double matchingOrderSettledBuyAmount = 0;

		    double user1BuyWalletReceiveAmount = 0;
		    double user1SellWalletDeductAmount = 0;
		    double user2BuyWalletReceiveAmount = 0;
		    double user2SellWalletDeductAmount = 0;

		    if (newOrder.getBuyAmount() <= matchingOrder.getSellAmount()) {
			newOrderSetteledBuyAmount = newOrder.getBuyAmount();
			matchingOrderSettledBuyAmount = newOrder.getSellAmount();

			user1BuyWalletReceiveAmount = newOrderSetteledBuyAmount;
			user1SellWalletDeductAmount = matchingOrderSettledBuyAmount;
			user2BuyWalletReceiveAmount = matchingOrderSettledBuyAmount;
			user2SellWalletDeductAmount = newOrderSetteledBuyAmount;

			SpotOrderTransaction newTransaction = new SpotOrderTransaction();
			newTransaction.setUser1(newOrder.getUser());
			newTransaction.setUser2(matchingOrder.getUser());
			newTransaction.setUser1BuyCurrency(newOrder.getBuyCurrency());
			newTransaction.setUser2BuyCurrency(matchingOrder.getBuyCurrency());
			newTransaction.setUser1BuyCurrencyAmount(newOrderSetteledBuyAmount);
			newTransaction.setUser2BuyCurrencyAmount(matchingOrderSettledBuyAmount);
			newTransaction.setTransactionTime(new Timestamp(System.currentTimeMillis()));
			spotOrderTransactionRepo.save(newTransaction);
			orderStatus = 1;

			newOrder.setBuyAmount(0);
			newOrder.setSellAmount(0);
			spotOrderForexRepo.save(newOrder);

			user1BuyWallet
				.setWalletBalance(user1BuyWallet.getWalletBalance() + user1BuyWalletReceiveAmount);
			walletRepo.save(user1BuyWallet);
			user1SellWallet
				.setWalletBalance(user1SellWallet.getWalletBalance() - user1SellWalletDeductAmount);
			walletRepo.save(user1SellWallet);

			user2BuyWallet
				.setWalletBalance(user2BuyWallet.getWalletBalance() + user2BuyWalletReceiveAmount);
			walletRepo.save(user2BuyWallet);
			user2SellWallet
				.setWalletBalance(user2SellWallet.getWalletBalance() - user2SellWalletDeductAmount);
			walletRepo.save(user2SellWallet);

			matchingOrder.setBuyAmount(matchingOrder.getBuyAmount() - matchingOrderSettledBuyAmount);
			matchingOrder.setSellAmount(matchingOrder.getBuyAmount() * matchingOrderRate);
			spotOrderForexRepo.save(matchingOrder);

			// newOrder.getBuyAmount() > matchingOrder.getSellAmount()
		    } else {
			newOrderSetteledBuyAmount = matchingOrder.getSellAmount();
			matchingOrderSettledBuyAmount = matchingOrder.getBuyAmount();

			user1BuyWalletReceiveAmount = newOrderSetteledBuyAmount;
			user1SellWalletDeductAmount = matchingOrderSettledBuyAmount;
			user2BuyWalletReceiveAmount = matchingOrderSettledBuyAmount;
			user2SellWalletDeductAmount = newOrderSetteledBuyAmount;

			SpotOrderTransaction newTransaction = new SpotOrderTransaction();
			newTransaction.setUser1(newOrder.getUser());
			newTransaction.setUser2(matchingOrder.getUser());
			newTransaction.setUser1BuyCurrency(newOrder.getBuyCurrency());
			newTransaction.setUser2BuyCurrency(matchingOrder.getBuyCurrency());
			newTransaction.setUser1BuyCurrencyAmount(newOrderSetteledBuyAmount);
			newTransaction.setUser2BuyCurrencyAmount(matchingOrderSettledBuyAmount);
			newTransaction.setTransactionTime(new Timestamp(System.currentTimeMillis()));
			spotOrderTransactionRepo.save(newTransaction);
			orderStatus = 1;

			matchingOrder.setBuyAmount(0);
			matchingOrder.setSellAmount(0);
			spotOrderForexRepo.save(matchingOrder);

			user1BuyWallet
				.setWalletBalance(user1BuyWallet.getWalletBalance() + user1BuyWalletReceiveAmount);
			walletRepo.save(user1BuyWallet);
			user1SellWallet
				.setWalletBalance(user1SellWallet.getWalletBalance() - user1SellWalletDeductAmount);
			walletRepo.save(user1SellWallet);

			user2BuyWallet
				.setWalletBalance(user2BuyWallet.getWalletBalance() + user2BuyWalletReceiveAmount);
			walletRepo.save(user2BuyWallet);
			user2SellWallet
				.setWalletBalance(user2SellWallet.getWalletBalance() - user2SellWalletDeductAmount);
			walletRepo.save(user2SellWallet);

			newOrder.setBuyAmount(newOrder.getBuyAmount() - newOrderSetteledBuyAmount);
			newOrder.setSellAmount(newOrder.getBuyAmount() / newOrderRate);
			spotOrderForexRepo.save(newOrder);
		    }
		}

		if (newOrder.getBuyAmount() == 0) {
		    orderStatus = 0;
		    break;
		}

	    }

	    if (newOrder.getBuyAmount() > 0) {
		for (int j = 0; j < marketOrderMatchingList.size(); j++) {
		    SpotOrderForex matchingOrder = marketOrderMatchingList.get(j);

		    Wallet user2BuyWallet = walletRepo.getByUserAndCurrency(matchingOrder.getUser(),
			    matchingOrder.getBuyCurrency());
		    if (user2BuyWallet == null) {
			user2BuyWallet = new Wallet();
			user2BuyWallet.setUser(matchingOrder.getUser());
			user2BuyWallet.setCurrency(matchingOrder.getBuyCurrency());
			user2BuyWallet.setWalletBalance(0);
			walletRepo.save(user2BuyWallet);
		    }
		    Wallet user2SellWallet = walletRepo.getByUserAndCurrency(matchingOrder.getUser(),
			    matchingOrder.getSellCurrency());

		    double newOrderSetteledBuyAmount = 0;
		    double matchingOrderSettledBuyAmount = 0;

		    double user1BuyWalletReceiveAmount = 0;
		    double user1SellWalletDeductAmount = 0;
		    double user2BuyWalletReceiveAmount = 0;
		    double user2SellWalletDeductAmount = 0;

		    if (newOrder.getSellAmount() <= matchingOrder.getBuyAmount()) {
			matchingOrderSettledBuyAmount = newOrder.getSellAmount();
			newOrderSetteledBuyAmount = newOrder.getBuyAmount();

			user1BuyWalletReceiveAmount = newOrderSetteledBuyAmount;
			user1SellWalletDeductAmount = matchingOrderSettledBuyAmount;
			user2BuyWalletReceiveAmount = matchingOrderSettledBuyAmount;
			user2SellWalletDeductAmount = newOrderSetteledBuyAmount;

			SpotOrderTransaction newTransaction = new SpotOrderTransaction();
			newTransaction.setUser1(newOrder.getUser());
			newTransaction.setUser2(matchingOrder.getUser());
			newTransaction.setUser1BuyCurrency(newOrder.getBuyCurrency());
			newTransaction.setUser2BuyCurrency(matchingOrder.getBuyCurrency());
			newTransaction.setUser1BuyCurrencyAmount(newOrderSetteledBuyAmount);
			newTransaction.setUser2BuyCurrencyAmount(matchingOrderSettledBuyAmount);
			newTransaction.setTransactionTime(new Timestamp(System.currentTimeMillis()));
			spotOrderTransactionRepo.save(newTransaction);
			orderStatus = 1;

			newOrder.setBuyAmount(0);
			newOrder.setSellAmount(0);
			spotOrderForexRepo.save(newOrder);

			user1BuyWallet
				.setWalletBalance(user1BuyWallet.getWalletBalance() + user1BuyWalletReceiveAmount);
			walletRepo.save(user1BuyWallet);
			user1SellWallet
				.setWalletBalance(user1SellWallet.getWalletBalance() - user1SellWalletDeductAmount);
			walletRepo.save(user1SellWallet);

			user2BuyWallet
				.setWalletBalance(user2BuyWallet.getWalletBalance() + user2BuyWalletReceiveAmount);
			walletRepo.save(user2BuyWallet);
			user2SellWallet
				.setWalletBalance(user2SellWallet.getWalletBalance() - user2SellWalletDeductAmount);
			walletRepo.save(user2SellWallet);

			matchingOrder.setBuyAmount(matchingOrder.getBuyAmount() - matchingOrderSettledBuyAmount);
			spotOrderForexRepo.save(matchingOrder);

			// newOrder.getSellAmount() > matchingOrder.getBuyAmount()
		    } else {
			matchingOrderSettledBuyAmount = matchingOrder.getBuyAmount();
			newOrderSetteledBuyAmount = matchingOrder.getBuyAmount() * newOrderRate;

			user1BuyWalletReceiveAmount = newOrderSetteledBuyAmount;
			user1SellWalletDeductAmount = matchingOrderSettledBuyAmount;
			user2BuyWalletReceiveAmount = matchingOrderSettledBuyAmount;
			user2SellWalletDeductAmount = newOrderSetteledBuyAmount;

			SpotOrderTransaction newTransaction = new SpotOrderTransaction();
			newTransaction.setUser1(newOrder.getUser());
			newTransaction.setUser2(matchingOrder.getUser());
			newTransaction.setUser1BuyCurrency(newOrder.getBuyCurrency());
			newTransaction.setUser2BuyCurrency(matchingOrder.getBuyCurrency());
			newTransaction.setUser1BuyCurrencyAmount(newOrderSetteledBuyAmount);
			newTransaction.setUser2BuyCurrencyAmount(matchingOrderSettledBuyAmount);
			newTransaction.setTransactionTime(new Timestamp(System.currentTimeMillis()));
			spotOrderTransactionRepo.save(newTransaction);
			orderStatus = 1;

			matchingOrder.setBuyAmount(0);
			spotOrderForexRepo.save(matchingOrder);

			user1BuyWallet
				.setWalletBalance(user1BuyWallet.getWalletBalance() + user1BuyWalletReceiveAmount);
			walletRepo.save(user1BuyWallet);
			user1SellWallet
				.setWalletBalance(user1SellWallet.getWalletBalance() - user1SellWalletDeductAmount);
			walletRepo.save(user1SellWallet);

			user2BuyWallet
				.setWalletBalance(user2BuyWallet.getWalletBalance() + user2BuyWalletReceiveAmount);
			walletRepo.save(user2BuyWallet);
			user2SellWallet
				.setWalletBalance(user2SellWallet.getWalletBalance() - user2SellWalletDeductAmount);
			walletRepo.save(user2SellWallet);

			newOrder.setBuyAmount(newOrder.getBuyAmount() - newOrderSetteledBuyAmount);
			newOrder.setSellAmount(newOrder.getBuyAmount() / newOrderRate);
			spotOrderForexRepo.save(newOrder);
		    }

		    if (newOrder.getBuyAmount() == 0) {
			orderStatus = 0;
			break;
		    }
		}

	    }

	    // Market Order
	} else {
	    Wallet user1BuyWallet = walletRepo.getByUserAndCurrency(currentUser, buyCurrency);
	    if (user1BuyWallet == null) {
		user1BuyWallet = new Wallet();
		user1BuyWallet.setUser(currentUser);
		user1BuyWallet.setCurrency(buyCurrency);
		user1BuyWallet.setWalletBalance(0);
		walletRepo.save(user1BuyWallet);
	    }
	    Wallet user1SellWallet = walletRepo.getByUserAndCurrency(currentUser, sellCurrency);

	    for (int i = 0; i < limitOrderMatchingList.size(); i++) {
		SpotOrderForex matchingOrder = limitOrderMatchingList.get(i);
		double matchingOrderRate = matchingOrder.getSellAmount() / matchingOrder.getBuyAmount();

		Wallet user2BuyWallet = walletRepo.getByUserAndCurrency(matchingOrder.getUser(),
			matchingOrder.getBuyCurrency());
		if (user2BuyWallet == null) {
		    user2BuyWallet = new Wallet();
		    user2BuyWallet.setUser(matchingOrder.getUser());
		    user2BuyWallet.setCurrency(matchingOrder.getBuyCurrency());
		    user2BuyWallet.setWalletBalance(0);
		    walletRepo.save(user2BuyWallet);
		}
		Wallet user2SellWallet = walletRepo.getByUserAndCurrency(matchingOrder.getUser(),
			matchingOrder.getSellCurrency());

		double newOrderSetteledBuyAmount = 0;
		double matchingOrderSettledBuyAmount = 0;

		double user1BuyWalletReceiveAmount = 0;
		double user1SellWalletDeductAmount = 0;
		double user2BuyWalletReceiveAmount = 0;
		double user2SellWalletDeductAmount = 0;

		if (newOrder.getBuyAmount() >= matchingOrder.getSellAmount()) {
		    newOrderSetteledBuyAmount = matchingOrder.getSellAmount();
		    matchingOrderSettledBuyAmount = matchingOrder.getBuyAmount();

		    user1BuyWalletReceiveAmount = newOrderSetteledBuyAmount;
		    user1SellWalletDeductAmount = matchingOrderSettledBuyAmount;
		    user2BuyWalletReceiveAmount = matchingOrderSettledBuyAmount;
		    user2SellWalletDeductAmount = newOrderSetteledBuyAmount;

		    SpotOrderTransaction newTransaction = new SpotOrderTransaction();
		    newTransaction.setUser1(newOrder.getUser());
		    newTransaction.setUser2(matchingOrder.getUser());
		    newTransaction.setUser1BuyCurrency(newOrder.getBuyCurrency());
		    newTransaction.setUser2BuyCurrency(matchingOrder.getBuyCurrency());
		    newTransaction.setUser1BuyCurrencyAmount(newOrderSetteledBuyAmount);
		    newTransaction.setUser2BuyCurrencyAmount(matchingOrderSettledBuyAmount);
		    newTransaction.setTransactionTime(new Timestamp(System.currentTimeMillis()));
		    spotOrderTransactionRepo.save(newTransaction);
		    orderStatus = 1;

		    matchingOrder.setBuyAmount(0);
		    matchingOrder.setSellAmount(0);
		    spotOrderForexRepo.save(matchingOrder);

		    user1BuyWallet.setWalletBalance(user1BuyWallet.getWalletBalance() + user1BuyWalletReceiveAmount);
		    walletRepo.save(user1BuyWallet);
		    user1SellWallet.setWalletBalance(user1SellWallet.getWalletBalance() - user1SellWalletDeductAmount);
		    walletRepo.save(user1SellWallet);

		    user2BuyWallet.setWalletBalance(user2BuyWallet.getWalletBalance() + user2BuyWalletReceiveAmount);
		    walletRepo.save(user2BuyWallet);
		    user2SellWallet.setWalletBalance(user2SellWallet.getWalletBalance() - user2SellWalletDeductAmount);
		    walletRepo.save(user2SellWallet);

		    newOrder.setBuyAmount(newOrder.getBuyAmount() - newOrderSetteledBuyAmount);
		    spotOrderForexRepo.save(newOrder);

		    // newOrder.getBuyAmount() < matchingOrder.getSellAmount()
		} else {

		    newOrderSetteledBuyAmount = newOrder.getBuyAmount();
		    matchingOrderSettledBuyAmount = newOrder.getBuyAmount() / matchingOrderRate;

		    user1BuyWalletReceiveAmount = newOrderSetteledBuyAmount;
		    user1SellWalletDeductAmount = matchingOrderSettledBuyAmount;
		    user2BuyWalletReceiveAmount = matchingOrderSettledBuyAmount;
		    user2SellWalletDeductAmount = newOrderSetteledBuyAmount;
		    
		    SpotOrderTransaction newTransaction = new SpotOrderTransaction();
		    newTransaction.setUser1(newOrder.getUser());
		    newTransaction.setUser2(matchingOrder.getUser());
		    newTransaction.setUser1BuyCurrency(newOrder.getBuyCurrency());
		    newTransaction.setUser2BuyCurrency(matchingOrder.getBuyCurrency());
		    newTransaction.setUser1BuyCurrencyAmount(newOrderSetteledBuyAmount);
		    newTransaction.setUser2BuyCurrencyAmount(matchingOrderSettledBuyAmount);
		    newTransaction.setTransactionTime(new Timestamp(System.currentTimeMillis()));
		    spotOrderTransactionRepo.save(newTransaction);
		    orderStatus = 1;
		    
		    newOrder.setBuyAmount(0);
		    spotOrderForexRepo.save(newOrder);
		    
		    user1BuyWallet.setWalletBalance(user1BuyWallet.getWalletBalance() + user1BuyWalletReceiveAmount);
		    walletRepo.save(user1BuyWallet);
		    user1SellWallet.setWalletBalance(user1SellWallet.getWalletBalance() - user1SellWalletDeductAmount);
		    walletRepo.save(user1SellWallet);

		    user2BuyWallet.setWalletBalance(user2BuyWallet.getWalletBalance() + user2BuyWalletReceiveAmount);
		    walletRepo.save(user2BuyWallet);
		    user2SellWallet.setWalletBalance(user2SellWallet.getWalletBalance() - user2SellWalletDeductAmount);
		    walletRepo.save(user2SellWallet);
		    
		    matchingOrder.setBuyAmount(matchingOrder.getBuyAmount() - matchingOrderSettledBuyAmount);
		    matchingOrder.setSellAmount(matchingOrder.getBuyAmount() * matchingOrderRate);
		    spotOrderForexRepo.save(matchingOrder);
		}
	    
		if (newOrder.getBuyAmount() == 0) {
		    orderStatus = 0;
		    break;
		}
	    }
	       
	    
	}

	if (orderStatus==0) {
	    redirectAttrs.addFlashAttribute("orderStatus", "filled");
	} else if (orderStatus==1) {
	    redirectAttrs.addFlashAttribute("orderStatus", "partial");
	}
    }
}
