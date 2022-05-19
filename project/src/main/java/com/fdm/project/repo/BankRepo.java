package com.fdm.project.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdm.project.model.Bank;
import com.fdm.project.model.Currency;
import com.fdm.project.model.User;

@Repository
public interface BankRepo extends JpaRepository<Bank, Integer>{
    List<Bank> getByUser(User user);
    Optional<List<Bank>> findByUser(User user);
    
    List<Bank> getByUserAndCurrency(User user, Currency currency);
}
