package com.fdm.project.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fdm.project.model.Currency;
import com.fdm.project.model.SpotOrderForex;
import com.fdm.project.model.User;

public interface SpotOrderForexRepo extends JpaRepository<SpotOrderForex, Integer>{
    List<SpotOrderForex> getByUser(User user);
    Optional<List<SpotOrderForex>> findByUser(User user);
    
    @Query("SELECT sof FROM SpotOrderForex sof "
    	+ "WHERE isActive = true "
    	+ "AND buyAmount > 0 "
    	+ "AND sellAmount > 0 "
	+ "AND expiryDate >= current_date "
    	+ "AND user <> :currentUser "
    	+ "ORDER BY sellAmount/buyAmount DESC, expiryDate ASC, orderId ASC")
    List<SpotOrderForex> getLimitOrdersListNotByUser(@Param("currentUser") User currentUser);
    
    @Query("SELECT sof FROM SpotOrderForex sof "
	+ "WHERE isActive = true "
	+ "AND buyAmount > 0 "
	+ "AND sellAmount = 0 "
	+ "AND expiryDate >= current_date "
	+ "AND user <> :currentUser "
	+ "ORDER BY expiryDate ASC, orderId ASC")
    List<SpotOrderForex> getMarketOrdersListNotByUser(@Param("currentUser") User currentUser);

    @Query("SELECT sof FROM SpotOrderForex sof "
	    	+ "WHERE isActive = true "
	    	+ "AND buyAmount > 0 "
	    	+ "AND sellAmount > 0 "
		+ "AND expiryDate >= current_date "
	    	+ "AND user <> :currentUser "
	    	+ "AND sellCurrency = :buyCurrency "
	    	+ "AND buyCurrency = :sellCurrency "
	    	+ "ORDER BY sellAmount/buyAmount DESC, expiryDate ASC, orderId ASC")
    List<SpotOrderForex> getLimitOrderMatchingList(@Param("currentUser") User currentUser, @Param("buyCurrency") Currency buyCurrency, @Param("sellCurrency") Currency sellCurrency);
    
    
    @Query("SELECT sof FROM SpotOrderForex sof "
	    	+ "WHERE isActive = true "
	    	+ "AND buyAmount > 0 "
	    	+ "AND sellAmount = 0 "
		+ "AND expiryDate >= current_date "
	    	+ "AND user <> :currentUser "
	    	+ "AND sellCurrency = :buyCurrency "
	    	+ "AND buyCurrency = :sellCurrency "
	    	+ "ORDER BY expiryDate ASC, orderId ASC")
    List<SpotOrderForex> getMarketOrderMatchingList(@Param("currentUser") User currentUser, @Param("buyCurrency") Currency buyCurrency, @Param("sellCurrency") Currency sellCurrency);
    
    
    @Query("SELECT sof FROM SpotOrderForex sof "
	    	+ "WHERE isActive = false "
	    	+ "OR buyAmount = 0 "
		+ "OR expiryDate < current_date")
    List<SpotOrderForex> getRemovableOrderList();
    

}


