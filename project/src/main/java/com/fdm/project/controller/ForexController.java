package com.fdm.project.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fdm.project.model.Currency;
import com.fdm.project.model.SpotOrderForex;
import com.fdm.project.model.SpotOrderTransaction;
import com.fdm.project.model.User;
import com.fdm.project.model.Wallet;
import com.fdm.project.service.CurrencyService;
import com.fdm.project.service.SpotOrderCleaningService;
import com.fdm.project.service.SpotOrderForexService;
import com.fdm.project.service.SpotOrderMatchingService;
import com.fdm.project.service.UserService;
import com.fdm.project.service.WalletService;

@Controller
public class ForexController {
    @Autowired
    private UserService userService;
    @Autowired
    private WalletService walletService;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private SpotOrderForexService spotOrderForexService;
    @Autowired
    private SpotOrderMatchingService matchingService;
    @Autowired
    private SpotOrderCleaningService cleaningService;
    
    @GetMapping("/{username}/forex")
    public String accessForexPage(@PathVariable String username, Model model, HttpServletRequest request) {
	HttpSession session = request.getSession();
	if (!userService.verifyLogin(username, session)) {
	    return "redirect:/login";
	}
	User currentUser = userService.getUserByUsername(username);
	if (session.getAttribute("spotOrderType")==null) {
	    session.setAttribute("spotOrderType", "market");
	    model.addAttribute("orderType","market");
	} else {
	    String orderType = (String) session.getAttribute("spotOrderType");
	    model.addAttribute("orderType",orderType);
	}
	model.addAttribute("newSpotOrder", new SpotOrderForex());
	
	List<Currency> buyCurrencies = currencyService.getAllCurrencies();
	model.addAttribute("buyCurrencies", buyCurrencies);
	
	List<Wallet> userWallets = walletService.getWalletByUser(currentUser);
	if (userWallets.size()<1) {
	    model.addAttribute("emptyWalletList", true);
	}
	model.addAttribute("userWallets", userWallets);
	
	boolean emptyLimitOrderList = spotOrderForexService.displayLimitOrderList(currentUser, model);
	boolean emptyMarketOrderList = spotOrderForexService.displayMarketOrderList(currentUser, model);
	
	model.addAttribute("emptyLimitOrderList", emptyLimitOrderList);
	model.addAttribute("emptyMarketOrderList", emptyMarketOrderList);
	
	return "forex";
    }
    
    @PostMapping("/{username}/forex/limitOrder")
    public String toggleLimitOrder(@PathVariable String username, HttpServletRequest request) {
	HttpSession session = request.getSession();
	session.setAttribute("spotOrderType", "limit");
	return "redirect:/"+ username + "/forex";
    }
    
    @PostMapping("/{username}/forex/marketOrder")
    public String toggleMarketOrder(@PathVariable String username, HttpServletRequest request) {
	HttpSession session = request.getSession();
	session.setAttribute("spotOrderType", "market");
	return "redirect:/"+ username + "/forex";
    }
    
    @PostMapping("/{username}/forex/submitOrder")
    public String submitMarketOrder(@PathVariable String username, @RequestParam String buyCurrencyId, @RequestParam String sellCurrencyId, SpotOrderForex newOrder, RedirectAttributes redirectAttrs) {
	User currentUser = userService.getUserByUsername(username);
	Currency buyCurrency = currencyService.getByCurrencyId(Integer.valueOf(buyCurrencyId));
	Currency sellCurrency = currencyService.getByCurrencyId(Integer.valueOf(sellCurrencyId));
	newOrder.setUser(currentUser);
	newOrder.setBuyCurrency(buyCurrency);
	newOrder.setSellCurrency(sellCurrency);
	newOrder.setActive(true);
	if (spotOrderForexService.isValidSpotOrder(newOrder, redirectAttrs)) {
	    spotOrderForexService.addNewSpotOrder(newOrder);
	    matchingService.matchSpotOrder(newOrder, redirectAttrs);
	}
	
	cleaningService.cleanUpSpotOrders();
	return "redirect:/"+ username + "/forex";
    }
}
