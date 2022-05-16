package com.fdm.project.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fdm.project.model.SpotOrderForex;
import com.fdm.project.model.User;

public interface SpotOrderForexRepo extends JpaRepository<SpotOrderForex, Integer>{
    List<SpotOrderForex> getByUser(User user);
    Optional<List<SpotOrderForex>> findByUser(User user);
    
    @Query("SELECT sof FROM SpotOrderForex sof "
    	+ "WHERE isActive = true "
    	+ "AND sellAmount > 0 "
    	+ "AND user <> :currentUser "
    	+ "ORDER BY expiryDate ASC")
    List<SpotOrderForex> getLimitOrdersListNotByUser(@Param("currentUser") User currentUser);
    
    @Query("SELECT sof FROM SpotOrderForex sof "
	    	+ "WHERE isActive = true "
	    	+ "AND sellAmount = 0 "
	    	+ "AND user <> :currentUser "
	    	+ "ORDER BY expiryDate ASC")
	    List<SpotOrderForex> getMarketOrdersListNotByUser(@Param("currentUser") User currentUser);
}
