package com.fdm.project.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdm.project.model.Currency;
import com.fdm.project.model.User;
import com.fdm.project.model.Wallet;

@Repository
public interface WalletRepo extends JpaRepository<Wallet, Integer>{
    List<Wallet> getByUser(User user);
    Optional<List<Wallet>> findByUser(User user);
    
    Wallet getByUserAndCurrency(User user, Currency currency);
}
