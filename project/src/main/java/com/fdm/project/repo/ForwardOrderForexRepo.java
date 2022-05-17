package com.fdm.project.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fdm.project.model.ForwardOrderForex;
import com.fdm.project.model.User;

@Repository
public interface ForwardOrderForexRepo extends JpaRepository<ForwardOrderForex, Integer>{
    List<ForwardOrderForex> getByUser(User user);
    Optional<List<ForwardOrderForex>> findByUser(User user);
    Optional<List<ForwardOrderForex>> findByIsActive(Boolean statement);
    
    List<ForwardOrderForex> getByIsActiveTrueAndUserIsNot(User currentUser);
    
    @Query("SELECT fof FROM ForwardOrderForex fof "
            + "WHERE isActive = false "
            + "OR closingDate < current_date")
    List<ForwardOrderForex> getRemovableOrders();
}
