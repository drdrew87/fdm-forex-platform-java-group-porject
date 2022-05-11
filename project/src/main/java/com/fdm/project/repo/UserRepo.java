package com.fdm.project.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdm.project.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{
    User getByUsername(String username);
    Optional<User> findByUsername(String username);
}
