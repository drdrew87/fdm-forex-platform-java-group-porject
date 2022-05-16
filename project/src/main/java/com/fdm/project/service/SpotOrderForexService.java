package com.fdm.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fdm.project.model.SpotOrderForex;
import com.fdm.project.model.User;
import com.fdm.project.repo.SpotOrderForexRepo;

@Service
public class SpotOrderForexService {
    @Autowired
    private SpotOrderForexRepo spotOrderForexRepo;
    
    public void addNewSpotOrder(SpotOrderForex order) {
	spotOrderForexRepo.save(order);
    }
    
    public void updateSpotOrder(SpotOrderForex order) {
	spotOrderForexRepo.save(order);
    }

    public boolean displayLimitOrderList(User currentUser, Model model) {
	boolean emptyLimitOrderList = true;
	
	List<SpotOrderForex> limitOrderList = spotOrderForexRepo.getLimitOrdersListNotByUser(currentUser);
	if (limitOrderList.size()>0) {
	    emptyLimitOrderList = false;
	}
	
	model.addAttribute("limitOrderList", limitOrderList);
	
	return emptyLimitOrderList;
    }

    public boolean displayMarketOrderList(User currentUser, Model model) {
	boolean emptyMarketOrderList = true;
	
	List<SpotOrderForex> marketOrderList = spotOrderForexRepo.getMarketOrdersListNotByUser(currentUser);
	if (marketOrderList.size()>0) {
	    emptyMarketOrderList = false;
	}
	
	model.addAttribute("marketOrderList", marketOrderList);
	
	return emptyMarketOrderList;
    }
}
