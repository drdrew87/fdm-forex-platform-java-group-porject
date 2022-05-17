package com.fdm.project.service;

import com.fdm.project.model.ForwardOrderForex;
import com.fdm.project.model.User;
import com.fdm.project.repo.ForwardOrderForexRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForwardOrderForexService {

    @Autowired
    private ForwardOrderForexRepo forwardOrderForexRepo;

    public List<ForwardOrderForex> findAllForwardOrderForex() {
        return forwardOrderForexRepo.findAll();
    }

    public List<ForwardOrderForex> findActiveForwardOrderForex() {
        return forwardOrderForexRepo.findByIsActive(true).get();
    }

    public List<ForwardOrderForex> getForwardOrderForexByUser(User user){
        return forwardOrderForexRepo.getByUser(user);
    }


}

