package com.fdm.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdm.project.model.SpotOrderForex;
import com.fdm.project.repo.SpotOrderForexRepo;

@Service
public class SpotOrderCleaningService {
    @Autowired
    private SpotOrderForexRepo spotOrderForexRepo;
    
    public void cleanUpSpotOrders() {
	List<SpotOrderForex> cleanUpOrderList = spotOrderForexRepo.getRemovableOrderList();
	spotOrderForexRepo.deleteAll(cleanUpOrderList);
    }

    
}
