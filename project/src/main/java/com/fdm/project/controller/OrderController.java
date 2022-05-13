package com.fdm.project.controller;

import com.fdm.project.model.ForwardOrderForex;
import com.fdm.project.service.ForwardOrderForexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    ForwardOrderForexService forwardOrderForexService;

    @GetMapping("/forward")
    public String goToForwardPage(Model model) {
        List<ForwardOrderForex> forwardOrderForexList=forwardOrderForexService.findAllForwardOrderForex();
        model.addAttribute("forwardorderlist",forwardOrderForexList);
        return "forward";


    }

    @GetMapping("/order")
    public String goToLoginPage(Model model) {

        return "order";
    }



    }
