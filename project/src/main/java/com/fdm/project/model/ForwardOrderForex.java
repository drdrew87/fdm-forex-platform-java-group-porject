package com.fdm.project.model;

import java.sql.Date;

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
public class ForwardOrderForex {
    @Id
    @SequenceGenerator(name = "FORWARD_ORDER_SEQ_GNTR", sequenceName = "FORWARD_ORDER_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "FORWARD_ORDER_SEQ_GNTR")
    @Column(name = "order_id")
    private int orderId;
    
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "buy_currency_id", referencedColumnName = "currency_id")
    private Currency buyCurrency;
    
    @Column(name = "buy_amount")
    private double buyAmount;
    
    @ManyToOne
    @JoinColumn(name = "sell_currency_id", referencedColumnName = "currency_id")
    private Currency sellCurrency;
    
    @Column(name = "sell_amount")
    private double sellAmount;
    
    @Column(name = "closing_date")
    private Date closingDate;
    
    @Column(name = "is_active")
    private boolean isActive;

    public ForwardOrderForex() {
	super();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Currency getBuyCurrency() {
        return buyCurrency;
    }

    public void setBuyCurrency(Currency buyCurrency) {
        this.buyCurrency = buyCurrency;
    }

    public double getBuyAmount() {
        return buyAmount;
    }

    public void setBuyAmount(double buyAmount) {
        this.buyAmount = buyAmount;
    }

    public Currency getSellCurrency() {
        return sellCurrency;
    }

    public void setSellCurrency(Currency sellCurrency) {
        this.sellCurrency = sellCurrency;
    }

    public double getSellAmount() {
        return sellAmount;
    }

    public void setSellAmount(double sellAmount) {
        this.sellAmount = sellAmount;
    }

    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getOrderId() {
        return orderId;
    }
    
    
}
