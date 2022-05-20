package com.fdm.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fdm.project.model.SpotOrderForex;
import com.fdm.project.repo.SpotOrderForexRepo;

@Service
@EnableScheduling
public class SpotOrderCleaningService {
    @Autowired
    private SpotOrderForexRepo spotOrderForexRepo;
    
    @Scheduled(fixedRate = 360000 )
    public void cleanUpSpotOrders() {
	System.out.println("Spot Orders Cleaning");
	List<SpotOrderForex> cleanUpOrderList = spotOrderForexRepo.getRemovableOrderList();
	spotOrderForexRepo.deleteAll(cleanUpOrderList);
    }

    
}
