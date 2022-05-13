package com.fdm.project.controller;

import com.fdm.project.model.Bank;
import com.fdm.project.model.User;
import com.fdm.project.model.Wallet;
import com.fdm.project.repo.WalletRepo;
import com.fdm.project.service.BankService;
import com.fdm.project.service.UserService;
import com.fdm.project.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class WalletController {
    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

    @Autowired
    private BankService bankService;

    @GetMapping("/{username}/wallet")
    public String gotoWallet(Model model, @PathVariable(name = "username") String username) {
        User user = userService.getUserByUsername(username);
        String preferredCurrency = (user.getPreferredCurrency()!=null) ? user.getPreferredCurrency().getCurrencyCode() : "USD";
        model.addAttribute("preferredCurrency", preferredCurrency);
        
        List<Wallet> listWallets = walletService.getWalletByUser(user);
        model.addAttribute("listWallets", listWallets);
        if (listWallets.size()==0) {
            model.addAttribute("emptyWalletList", true);
        }

        List<Bank> listBanks = bankService.getBankByUser(user);
        model.addAttribute("listBanks", listBanks);
        if (listBanks.size()==0) {
            System.out.println("empty");
            model.addAttribute("emptyBankList", true);
        }

        Double totalValue = walletService.totalValue(user);
        model.addAttribute("totalValue", totalValue);
        
        return "wallet";
    }
}








