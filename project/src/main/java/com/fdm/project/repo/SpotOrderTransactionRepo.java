package com.fdm.project.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdm.project.model.SpotOrderTransaction;
import com.fdm.project.model.User;

public interface SpotOrderTransactionRepo extends JpaRepository<SpotOrderTransaction, Integer>{
    List<SpotOrderTransaction> getByUser1(User user1);
    Optional<List<SpotOrderTransaction>> findByUser1(User user1);
    
    List<SpotOrderTransaction> getByUser2(User user2);
    Optional<List<SpotOrderTransaction>> findByUser2(User user2);
}