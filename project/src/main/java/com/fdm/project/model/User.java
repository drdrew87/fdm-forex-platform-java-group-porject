package com.fdm.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    
    @Column(name = "username")
    private String username;
    
    @Column(name = "password")
    private String password;

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

    public int getUserId() {
        return userId;
    }

}
