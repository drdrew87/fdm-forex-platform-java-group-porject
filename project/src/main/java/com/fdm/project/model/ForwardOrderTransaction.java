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
public class ForwardOrderTransaction {
    @Id
    @SequenceGenerator(name = "FORWARD_TRANSACTION_SEQ_GNTR", sequenceName = "FORWARD_TRANSACTION_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "FORWARD_TRANSACTION_SEQ_GNTR")
    @Column(name = "transaction_id")
    private int transactionId;
    
    @ManyToOne
    @JoinColumn(name = "order_creator_id", referencedColumnName = "user_id")
    private User orderCreator;
    
    @ManyToOne
    @JoinColumn(name = "user2_id", referencedColumnName = "user_id")
    private User user2;
    
    @Column(name = "transaction_time")
    private Timestamp transactionTime;
    
    @ManyToOne
    @JoinColumn(name = "transaction_currency_id", referencedColumnName = "currency_id")
    private Currency transactionCurrency;
    
    @Column(name = "creator_received_amount")
    private double creatorReceivedAmount;

    public ForwardOrderTransaction() {
	super();
    }

    public User getOrderCreator() {
        return orderCreator;
    }

    public void setOrderCreator(User orderCreator) {
        this.orderCreator = orderCreator;
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

    public Currency getTransactionCurrency() {
        return transactionCurrency;
    }

    public void setTransactionCurrency(Currency transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
    }

    public double getCreatorReceivedAmount() {
        return creatorReceivedAmount;
    }

    public void setCreatorReceivedAmount(double creatorReceivedAmount) {
        this.creatorReceivedAmount = creatorReceivedAmount;
    }

    public int getTransactionId() {
        return transactionId;
    }
    
    
}
