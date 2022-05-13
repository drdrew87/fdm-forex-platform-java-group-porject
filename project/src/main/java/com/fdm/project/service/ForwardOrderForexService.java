package com.fdm.project.service;

import com.fdm.project.model.ForwardOrderForex;
import com.fdm.project.repo.ForwardOrderForexRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ForwardOrderForexService {
    @Autowired
    ForwardOrderForexRepo forwardOrderForexRepo;

    List<ForwardOrderForex> getAllForwardOrderForex() {
        return forwardOrderForexRepo.findAll();
    }
}
