package com.fdm.project.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdm.project.model.Currency;

@Repository
public interface CurrencyRepo extends JpaRepository<Currency, Integer>{
    Currency getByCurrencySymbol(String symbol);
    Optional<Currency> findByCurrencySymbol(String symbol);
}
