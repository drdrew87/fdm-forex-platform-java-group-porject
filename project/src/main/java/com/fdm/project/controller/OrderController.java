package com.fdm.project.controller;

import com.fdm.project.model.ForwardOrderForex;
import com.fdm.project.model.SpotOrderForex;
import com.fdm.project.service.ForwardOrderForexService;
import com.fdm.project.service.SpotOrderForexService;
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

    @Autowired
    SpotOrderForexService spotOrderForexService;


    @GetMapping("/forward")
    public String goToForwardPage(Model model) {
        forwardOrderForexService.dumForwardOrder();
        forwardOrderForexService.fakeForwardOrder();
        List<ForwardOrderForex> forwardOrderForexList = forwardOrderForexService.findActiveForwardOrderForex();
        model.addAttribute("forwardorderlist", forwardOrderForexList);
        return "forward";
    }

    @GetMapping("/orderboard")
    public String goToOrderBoardPage(Model model) {
        spotOrderForexService.fakeSpotOrder();
        spotOrderForexService.dumSpotOrder();
        forwardOrderForexService.dumForwardOrder();
        forwardOrderForexService.fakeForwardOrder();
        List<ForwardOrderForex> forwardOrderForexList = forwardOrderForexService.findActiveForwardOrderForex();
        model.addAttribute("forwardorderlist", forwardOrderForexList);
        List<SpotOrderForex> spotOrderForexList = spotOrderForexService.findActiveSpotOrderForex();
        model.addAttribute("spotorderforexlist", spotOrderForexList);
        return "orderboard";
    }


}
