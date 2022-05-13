package com.fdm.project.service;

import com.fdm.project.model.SpotOrderForex;
import com.fdm.project.repo.SpotOrderForexRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SpotOrderForexService {
    @Autowired
    private SpotOrderForexRepo spotOrderForexRepo;

    public List<SpotOrderForex> findAllSpotOrderForex(){
        return spotOrderForexRepo.findAll();

    }
}
