package com.fdm.project.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LogOutController {
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
	HttpSession session = request.getSession();
	session.invalidate();
	return "redirect:/login";
    }
}
