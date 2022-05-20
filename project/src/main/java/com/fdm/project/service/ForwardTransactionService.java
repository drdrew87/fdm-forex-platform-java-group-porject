package com.fdm.project.service;
import com.fdm.project.model.ForwardOrderTransaction;
import com.fdm.project.repo.ForwardOrderTransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fdm.project.model.User;
import com.fdm.project.repo.UserRepo;

import java.util.List;

@Service
public class ForwardTransactionService {

    @Autowired
    private ForwardOrderTransactionRepo forwardOrderTransactionRepo;

    public List<ForwardOrderTransaction> getAllSpotOrderTransactionHistory() {
        return forwardOrderTransactionRepo.findAll();
    }

    public List<ForwardOrderTransaction> getForwardOrderTransactionByUser(User user) {
        return forwardOrderTransactionRepo.getByOrderCreator(user);
    }

    public List<ForwardOrderTransaction> getPendingTransactions(User user) {
	return forwardOrderTransactionRepo.getOpenPendingTransactions(user);
    }

    public List<ForwardOrderTransaction> getClosedBoughtTransactions(User user) {
	return forwardOrderTransactionRepo.getClosedBoughtTransactions(user);
    }
}