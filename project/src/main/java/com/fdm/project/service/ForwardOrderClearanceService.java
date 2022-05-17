package com.fdm.project.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fdm.project.model.Currency;
import com.fdm.project.model.ForwardOrderForex;
import com.fdm.project.model.ForwardOrderTransaction;
import com.fdm.project.model.User;
import com.fdm.project.model.Wallet;
import com.fdm.project.repo.ForwardOrderForexRepo;
import com.fdm.project.repo.ForwardOrderTransactionRepo;
import com.fdm.project.repo.WalletRepo;

@Service
@EnableScheduling
public class ForwardOrderClearanceService {
    @Autowired
    private ForwardOrderForexRepo orderRepo;
    @Autowired
    private ForwardOrderTransactionRepo transactionRepo;
    @Autowired
    private WalletRepo walletRepo;
    
    
    @Scheduled(fixedRate = 360000 )
    public void clearInactiveExpiredForwardOrders() {
	System.out.println("Forward Order Clearance");
	List<ForwardOrderForex> removableOrders = orderRepo.getRemovableOrders();
	orderRepo.deleteAll(removableOrders);
    }
    
    @Scheduled(fixedRate = 360000 )
    public void completePendingTransactions() {
	System.out.println("Pending Transaction Clearance");
	List<ForwardOrderTransaction> closingTransactions = transactionRepo.getClosingPendingTransactions();
	
	for (int i = 0; i < closingTransactions.size(); i++) {
	    ForwardOrderTransaction closingTransaction = closingTransactions.get(i);
	    User orderCreator = closingTransaction.getOrderCreator();
	    User user2 = closingTransaction.getUser2();
	    Currency transactionCurrency = closingTransaction.getTransactionCurrency();
	    Currency sellCurrency = closingTransaction.getOrderSellCurrency();
	    Wallet creatorBuyWallet = walletRepo.getByUserAndCurrency(orderCreator, transactionCurrency);
	    Wallet user2BuyWallet = walletRepo.getByUserAndCurrency(user2, transactionCurrency);
	    
	    double currentRate = transactionCurrency.getCurrencyRate() / sellCurrency.getCurrencyRate();
	    double receivedAmount = (currentRate - closingTransaction.getOrderExchangeRate())*closingTransaction.getCreatorReceivedAmount();
	    
	    closingTransaction.setTransactionTime(new Timestamp(System.currentTimeMillis()));
	    closingTransaction.setCreatorReceivedAmount(receivedAmount);
	    transactionRepo.save(closingTransaction);
	    
	    creatorBuyWallet.setWalletBalance(creatorBuyWallet.getWalletBalance()+receivedAmount);
	    walletRepo.save(creatorBuyWallet);
	    user2BuyWallet.setWalletBalance(user2BuyWallet.getWalletBalance()-receivedAmount);
	    walletRepo.save(user2BuyWallet);
	}
    }
}
