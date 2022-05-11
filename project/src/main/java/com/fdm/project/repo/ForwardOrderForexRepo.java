package com.fdm.project.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdm.project.model.ForwardOrderForex;
import com.fdm.project.model.User;

@Repository
public interface ForwardOrderForexRepo extends JpaRepository<ForwardOrderForex, Integer>{
    List<ForwardOrderForex> getByUser(User user);
    Optional<List<ForwardOrderForex>> findByUser(User user);
}
