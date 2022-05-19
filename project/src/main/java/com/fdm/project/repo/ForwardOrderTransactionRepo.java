package com.fdm.project.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fdm.project.model.ForwardOrderTransaction;
import com.fdm.project.model.User;

@Repository
public interface ForwardOrderTransactionRepo extends JpaRepository<ForwardOrderTransaction, Integer>{
    List<ForwardOrderTransaction> getByOrderCreator(User orderCreator);
    Optional<List<ForwardOrderTransaction>> findByOrderCreator(User orderCreator);
    List<ForwardOrderTransaction> findAll();
    
    @Query("SELECT fot FROM ForwardOrderTransaction fot "
    	+ "WHERE transactionTime is null "
    	+ "AND orderClosingDate < current_date")
    List<ForwardOrderTransaction> getClosingPendingTransactions();
    
    @Query("SELECT fot FROM ForwardOrderTransaction fot "
    	+ "WHERE transactionTime is null "
    	+ "AND orderClosingDate > current_date "
    	+ "AND user2 = :user")
    List<ForwardOrderTransaction> getOpenPendingTransactions(@Param("user") User user);
    
    
    
    @Query("SELECT fot FROM ForwardOrderTransaction fot "
	    	+ "WHERE transactionTime is not null "
	    	+ "AND orderClosingDate < current_date "
	    	+ "AND user2 = :user")
    List<ForwardOrderTransaction> getClosedBoughtTransactions(@Param("user") User user);
}
