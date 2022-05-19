package com.fdm.project.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fdm.project.model.User;
import com.fdm.project.service.CurrencyService;
import com.fdm.project.service.UserService;

@Controller
public class LoginRegisterController {

	@Autowired
	private UserService userService;
	@Autowired
	private CurrencyService currencyService;
	
	@GetMapping("/")
	public String goToLandingPage() {
		return "landing";
	}
	
	@GetMapping("/login")
	public String goToLoginPage() {	
		return "login";
	}

	@PostMapping("/login")
	public String verifyUserForLogin(@RequestParam String username, @RequestParam String password, HttpServletRequest req) {
		
		boolean isValidUser = userService.verifyUser(username, password);
		
		if (isValidUser) {
			HttpSession session = req.getSession();
			session.setAttribute("active_user", username);
			return "redirect:/" + username + "/portfolio";
		} else {
			return "login";
		}

	}
	
	@GetMapping("/register")
	public String goToRegisterpage(Model model) {
		
		model.addAttribute("user", new User());
		model.addAttribute("currencies", currencyService.getAllCurrencies());
		
		return "register";
	}
	
	@PostMapping("/register")
	public String registerNewUser(@ModelAttribute User user, @RequestParam String preferredcurrency) {
		
		boolean isUniqueUsername = userService.verifyUsernameIsUnique(user.getUsername());
		
		if (isUniqueUsername) {
			user.setPreferredCurrency(currencyService.getByCurrencyCode(preferredcurrency));
			userService.registerUser(user);
			return "login";
		} else {
		return "register";
		}
	}
	
	
}

