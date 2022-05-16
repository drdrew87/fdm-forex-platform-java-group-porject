package com.fdm.project.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdm.project.model.SpotOrderForex;
import com.fdm.project.model.User;

public interface SpotOrderForexRepo extends JpaRepository<SpotOrderForex, Integer>{
    List<SpotOrderForex> getByUser(User user);
    Optional<List<SpotOrderForex>> findByUser(User user);
    Optional<List<SpotOrderForex>> findByIsActive(Boolean statement);
}
