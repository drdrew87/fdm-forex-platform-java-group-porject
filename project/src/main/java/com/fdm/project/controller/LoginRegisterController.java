package com.fdm.project.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fdm.project.service.UserService;

@Controller
public class LoginRegisterController {

	@Autowired
	private UserService userService;
	
	
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
		
		boolean verified = userService.verifyUser(username, password);
		
		if (verified) {
			HttpSession session = req.getSession();
			session.setAttribute("active_user", username);
			return "home";
		} else {
			return "login";
		}
		
		
	}
}
