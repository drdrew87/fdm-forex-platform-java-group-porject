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
public class User {
    @Id
    @SequenceGenerator(name = "USER_SEQ_GNTR", sequenceName = "USER_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "USER_SEQ_GNTR")
    @Column(name = "user_id")
    private int userId;
    
    @Column(name = "username", unique = true)
    private String username;
    
    @Column(name = "password")
    private String password;
    
    @ManyToOne
    @JoinColumn(name = "preferred_currency_id", referencedColumnName = "currency_id")
    private Currency preferredCurrency;

    public User() {
	super();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Currency getPreferredCurrency() {
        return preferredCurrency;
    }

    public void setPreferredCurrency(Currency preferredCurrency) {
        this.preferredCurrency = preferredCurrency;
    }

    public int getUserId() {
        return userId;
    }

}
