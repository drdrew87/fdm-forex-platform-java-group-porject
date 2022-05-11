package com.fdm.project.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.springframework.stereotype.Component;

@Component
@Entity
public class SpotOrderTransaction {
    @Id
    @SequenceGenerator(name = "SPOT_TRANSACTION_SEQ_GNTR", sequenceName = "SPOT_TRANSACTION_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SPOT_TRANSACTION_SEQ_GNTR")
    @Column(name = "transaction_id")
    private int transactionId;
    
    @ManyToOne
    @JoinColumn(name = "user1_id", referencedColumnName = "user_id")
    private User user1;
    
    @ManyToOne
    @JoinColumn(name = "user2_id", referencedColumnName = "user_id")
    private User user2;
    
    @Column(name = "transaction_time")
    private Timestamp transactionTime;
    
    @ManyToOne
    @JoinColumn(name = "user1_buy_currency_id", referencedColumnName = "currency_id")
    private Currency user1BuyCurrency;
    
    @Column(name = "user1_buy_currency_amount")
    private double user1BuyCurrencyAmount;
    
    @ManyToOne
    @JoinColumn(name = "user2_buy_currency_id", referencedColumnName = "currency_id")
    private Currency user2BuyCurrency;
    
    @Column(name = "user2_buy_currency_amount")
    private double user2BuyCurrencyAmount;

    public SpotOrderTransaction() {
	super();
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public Timestamp getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Timestamp transactionTime) {
        this.transactionTime = transactionTime;
    }

    public Currency getUser1BuyCurrency() {
        return user1BuyCurrency;
    }

    public void setUser1BuyCurrency(Currency user1BuyCurrency) {
        this.user1BuyCurrency = user1BuyCurrency;
    }

    public double getUser1BuyCurrencyAmount() {
        return user1BuyCurrencyAmount;
    }

    public void setUser1BuyCurrencyAmount(double user1BuyCurrencyAmount) {
        this.user1BuyCurrencyAmount = user1BuyCurrencyAmount;
    }

    public Currency getUser2BuyCurrency() {
        return user2BuyCurrency;
    }

    public void setUser2BuyCurrency(Currency user2BuyCurrency) {
        this.user2BuyCurrency = user2BuyCurrency;
    }

    public double getUser2BuyCurrencyAmount() {
        return user2BuyCurrencyAmount;
    }

    public void setUser2BuyCurrencyAmount(double user2BuyCurrencyAmount) {
        this.user2BuyCurrencyAmount = user2BuyCurrencyAmount;
    }

    public int getTransactionId() {
        return transactionId;
    }
}
