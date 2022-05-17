package com.fdm.project.controller;

import com.fdm.project.model.ForwardOrderTransaction;
import com.fdm.project.model.SpotOrderTransaction;
import com.fdm.project.model.User;
import com.fdm.project.model.Wallet;
import com.fdm.project.service.ForwardTransactionService;
import com.fdm.project.service.SpotTransactionService;
import com.fdm.project.service.UserService;
import com.fdm.project.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class PortfolioController {

    @Autowired
    private UserService userService;
    @Autowired
    private WalletService walletService;

    @Autowired
    private SpotTransactionService spotTransactionService;

    @Autowired
    private ForwardTransactionService forwardTransactionService;


    @GetMapping("/{username}/portfolio")
    public String goToPortfolio(@PathVariable String username, Model model) {
        User user = userService.getUserByUsername(username);
        List<Wallet> listWallets = walletService.getWalletByUser(user);
        model.addAttribute("listWallets", listWallets);
        if (listWallets.size() == 0) {
            model.addAttribute("emptyWalletList", true);
        }

        String preferredCurrency = (user.getPreferredCurrency()!=null) ? user.getPreferredCurrency().getCurrencyCode() : "USD";
        model.addAttribute("preferredCurrency", preferredCurrency);

        Double totalValue = walletService.totalValue(user);
        model.addAttribute("totalValue", totalValue);

        List<SpotOrderTransaction> listSpotOrderTransactionHistory = spotTransactionService.getAllSpotOrderTransactionHistory();
        model.addAttribute("listSpotOrderTransactionHistory",listSpotOrderTransactionHistory);

        List<ForwardOrderTransaction> listForwardOrderTransaction = forwardTransactionService.getAllSpotOrderTransactionHistory();
        model.addAttribute("listForwardOrderTransaction",listForwardOrderTransaction);

        return "portfolio";
    }
}
