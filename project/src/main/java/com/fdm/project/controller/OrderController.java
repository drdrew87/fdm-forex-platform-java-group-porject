package com.fdm.project.controller;

import com.fdm.project.model.ForwardOrderForex;
import com.fdm.project.model.SpotOrderForex;
import com.fdm.project.service.ForwardOrderForexService;
import com.fdm.project.service.SpotOrderForexService;
import com.fdm.project.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private UserService userService;
    @Autowired
    ForwardOrderForexService forwardOrderForexService;

    @Autowired
    SpotOrderForexService spotOrderForexService;


    @GetMapping("/{username}/orderboard")
    public String goToOrderBoardPage(@PathVariable String username, Model model, HttpServletRequest request) {
	HttpSession session = request.getSession();
	if (!userService.verifyLogin(username, session)) {
	    return "redirect:/login";
	}
	
	List<ForwardOrderForex> forwardOrderForexList = forwardOrderForexService.findActiveForwardOrderForex();
        model.addAttribute("forwardorderlist", forwardOrderForexList);
        List<SpotOrderForex> activeLimitSpotOrderForexList = spotOrderForexService.findActiveLimitSpotOrderForex();
        model.addAttribute("limitspotorderforexlist", activeLimitSpotOrderForexList);
        List<SpotOrderForex> activeMarketSpotOrderForexList = spotOrderForexService.findActiveMarketSpotOrderForex();
        model.addAttribute("marketspotorderforexlist", activeMarketSpotOrderForexList);
        return "orderboard";
    }


}
