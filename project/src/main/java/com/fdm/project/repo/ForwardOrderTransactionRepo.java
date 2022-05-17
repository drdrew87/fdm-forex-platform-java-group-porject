package com.fdm.project.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdm.project.model.ForwardOrderTransaction;
import com.fdm.project.model.User;

@Repository
public interface ForwardOrderTransactionRepo extends JpaRepository<ForwardOrderTransaction, Integer>{
    List<ForwardOrderTransaction> getByOrderCreator(User orderCreator);
    Optional<List<ForwardOrderTransaction>> findByOrderCreator(User orderCreator);
    List<ForwardOrderTransaction> findAll();
}
