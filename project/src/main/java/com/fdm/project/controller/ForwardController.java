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
import com.fdm.project.model.ForwardOrderForex;
import com.fdm.project.model.ForwardOrderTransaction;
import com.fdm.project.model.User;
import com.fdm.project.model.Wallet;
import com.fdm.project.service.CurrencyService;
import com.fdm.project.service.ForwardOrderForexService;
import com.fdm.project.service.ForwardOrderTransactionService;
import com.fdm.project.service.UserService;
import com.fdm.project.service.WalletService;

@Controller
public class ForwardController {
    @Autowired
    private UserService userService;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private WalletService walletService;
    @Autowired
    private ForwardOrderForexService forwardOrderService;
    @Autowired
    private ForwardOrderTransactionService orderTransactionService;

    @GetMapping("/{username}/forward")
    public String accessForwardPage(@PathVariable String username, Model model, HttpServletRequest request) {
	HttpSession session = request.getSession();
	if (!userService.verifyLogin(username, session)) {
	    return "redirect:/login";
	}
	User currentUser = userService.getUserByUsername(username);

	model.addAttribute("newForwardOrder", new ForwardOrderForex());

	List<Currency> buyCurrencies = currencyService.getAllCurrencies();
	model.addAttribute("buyCurrencies", buyCurrencies);

	List<Wallet> userWallets = walletService.getWalletByUser(currentUser);
	if (userWallets.size() < 1) {
	    model.addAttribute("emptyWalletList", true);
	}
	model.addAttribute("userWallets", userWallets);
	
	boolean emptyForwardOrderList = forwardOrderService.displayForwardOrderList(currentUser, model);
	
	model.addAttribute("emptyForwardOrderList", emptyForwardOrderList);
	
	return "forward";
    }
    
    @PostMapping("/{username}/forward/submitOrder")
    public String submitNewForwardOrder(@PathVariable String username, @RequestParam String buyCurrencyId, @RequestParam String sellCurrencyId, ForwardOrderForex newForwardOrder, RedirectAttributes redirectAttr) {
	User currentUser = userService.getUserByUsername(username);
	Currency buyCurrency = currencyService.getByCurrencyId(Integer.valueOf(buyCurrencyId));
	Currency sellCurrency = currencyService.getByCurrencyId(Integer.valueOf(sellCurrencyId));
	
	newForwardOrder.setUser(currentUser);
	newForwardOrder.setBuyCurrency(buyCurrency);
	newForwardOrder.setSellCurrency(sellCurrency);
	newForwardOrder.setActive(true);
	if (forwardOrderService.validateNewForwardOrder(newForwardOrder, redirectAttr)) {
	    forwardOrderService.addNewForwardOrder(newForwardOrder);
	}
	return "redirect:/" + username + "/forward";
    }
    
    @PostMapping("/{username}/forward/submitBuyOrder")
    public String submitBuyOrder(@PathVariable String username, @RequestParam String buyOrderId, Model model, RedirectAttributes redirectAttr) {
	User currentUser = userService.getUserByUsername(username);
	ForwardOrderForex buyOrder = forwardOrderService.getForwardOrderById(Integer.valueOf(buyOrderId));
	
	String returnPage = forwardOrderService.validateBuyOrder(currentUser, buyOrder, model, redirectAttr);
	return returnPage;
    }
    
    @PostMapping("/{username}/forward/confirmBuyOrder")
    public String confirmBuyOrder(@PathVariable String username, @RequestParam String buyOrderId) {
	User currentUser = userService.getUserByUsername(username);
	ForwardOrderForex buyOrder = forwardOrderService.getForwardOrderById(Integer.valueOf(buyOrderId));
	
	
	
	ForwardOrderTransaction newTransaction = new ForwardOrderTransaction();
	newTransaction.setOrderCreator(buyOrder.getUser());
	newTransaction.setUser2(currentUser);
	newTransaction.setTransactionCurrency(buyOrder.getBuyCurrency());
	newTransaction.setOrderSellCurrency(buyOrder.getSellCurrency());
	newTransaction.setOrderExchangeRate(buyOrder.getBuyAmount()/buyOrder.getSellAmount());
	newTransaction.setOrderClosingDate(buyOrder.getClosingDate());
	newTransaction.setCreatorReceivedAmount(buyOrder.getBuyAmount());
	orderTransactionService.addNewForwardTransaction(newTransaction);
	
	buyOrder.setActive(false);
	forwardOrderService.updateForwardOrder(buyOrder);
	
	
	return "redirect:/" + username + "/forward";
    }
    
}
