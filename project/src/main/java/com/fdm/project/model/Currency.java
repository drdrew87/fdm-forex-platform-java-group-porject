package com.fdm.project.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.springframework.stereotype.Component;

@Component
@Entity
public class Currency {
    @Id
    @SequenceGenerator(name = "CURRENCY_SEQ_GNTR", sequenceName = "CURRENCY_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "CURRENCY_SEQ_GNTR")
    @Column(name = "currency_id")
    private int currencyId;
    
    @Column(name = "currency_name")
    private String currencyName;
    
    @Column(name = "currency_symbol", unique = true)
    private String currencySymbol;
    
    @Column(name = "currency_rate")
    private double currencyRate;
    
    @Column(name = "last_update_time")
    private Timestamp lastUpdateTime;

    public Currency() {
	super();
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public double getCurrencyRate() {
        return currencyRate;
    }

    public void setCurrencyRate(double currencyRate) {
        this.currencyRate = currencyRate;
    }

    public Timestamp getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Timestamp lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public int getCurrencyId() {
        return currencyId;
    }
}
