package com.fdm.project.service;

import com.fdm.project.model.ForwardOrderForex;
import com.fdm.project.repo.ForwardOrderForexRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ForwardOrderForexService {
    @Autowired
    private ForwardOrderForexRepo forwardOrderForexRepo;

    public List<ForwardOrderForex> findAllForwardOrderForex() {
        return forwardOrderForexRepo.findAll();
    }
}
