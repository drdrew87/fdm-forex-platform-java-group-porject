package com.fdm.project.service;

import com.fdm.project.model.SpotOrderForex;
import com.fdm.project.repo.SpotOrderForexRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SpotOrderForexService {
    @Autowired
    SpotOrderForexRepo spotOrderForexRepo;

    List<SpotOrderForex> findAllSpotOrderForex(){
        return spotOrderForexRepo.findAll();

    }
}
