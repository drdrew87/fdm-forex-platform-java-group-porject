package com.fdm.project.controller;

import com.fdm.project.model.*;
import com.fdm.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private SpotOrderTransactionService spotOrderTransactionService;

    @Autowired
    private ForwardTransactionService forwardTransactionService;

    @Autowired
    private SpotOrderForexService spotOrderForexService;

    @Autowired
    private ForwardOrderForexService forwardOrderForexService;

//    @Autowired
//    private

    @GetMapping("/{username}/myProfile")
    public String goToPortfolio(@PathVariable String username, Model model, HttpServletRequest request) {
	HttpSession session = request.getSession();
	if (!userService.verifyLogin(username, session)) {
	    return "redirect:/login";
	}
	User user = userService.getUserByUsername(username);

        List<SpotOrderForex> listCurrentSpotOrders = spotOrderForexService.getCurrentSpotOrderByUser(user);
        model.addAttribute("listCurrentSpotOrders", listCurrentSpotOrders);

        List<ForwardOrderForex> listCurrentForwardOrders = forwardOrderForexService.getForwardOrderForexByUser(user);
        model.addAttribute("listCurrentForwardOrders", listCurrentForwardOrders);

        List<SpotOrderTransaction> listSpotTransactions = spotOrderTransactionService.getSpotOrderTransactionByUser(user);
        model.addAttribute("listSpotOrderTransactionHistory", listSpotTransactions);
        if (listSpotTransactions.size() == 0) {
            model.addAttribute("noSpotOrderTransactions", true);
        }

        List<ForwardOrderTransaction> listForwardOrderTransaction = forwardTransactionService.getForwardOrderTransactionByUser(user);
        model.addAttribute("listForwardOrderTransaction",listForwardOrderTransaction);
        if (listForwardOrderTransaction.size() == 0) {
            model.addAttribute("noForwardOrderTransaction", true);
        }
        
        List<ForwardOrderTransaction> listPendingTransaction = forwardTransactionService.getPendingTransactions(user);
        model.addAttribute("listPendingTransaction",listPendingTransaction);
        if (listForwardOrderTransaction.size() == 0) {
            model.addAttribute("noPendingTransaction", true);
        }
        
        List<ForwardOrderTransaction> listClosedBoughtTransactions = forwardTransactionService.getClosedBoughtTransactions(user);
        model.addAttribute("listClosedBoughtTransactions",listClosedBoughtTransactions);
        if (listForwardOrderTransaction.size() == 0) {
            model.addAttribute("noPendingTransaction", true);
        }

        return "myprofile";
    }

}
