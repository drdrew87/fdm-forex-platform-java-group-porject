package com.fdm.project.model;

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
public class Wallet {
    @Id
    @SequenceGenerator(name = "WALLET_SEQ_GNTR", sequenceName = "WALLET_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "WALLET_SEQ_GNTR")
    private int walletId;
    
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "currency_id", referencedColumnName = "currency_id")
    private Currency currency;
    
    @Column(name = "wallet_balance")
    private double walletBalance;

    public Wallet() {
	super();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(double walletBalance) {
        this.walletBalance = walletBalance;
    }

    public int getWalletId() {
        return walletId;
    }
    
    
}
