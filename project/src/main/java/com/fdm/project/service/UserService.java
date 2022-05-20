package com.fdm.project.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdm.project.model.User;
import com.fdm.project.repo.UserRepo;

@Service
public class UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	public boolean verifyUser(String username, String password) {
		
		User user = userRepo.getByUsername(username);
		
		if (user!=null && user.getPassword().equals(password)) {
			return true;
		} else {
		return false;
		}
	}

	public boolean verifyUsernameIsUnique(String username) {
		
		User user = userRepo.getByUsername(username);
		if (user == null) {
			return true;
		} else {
		return false;
		}
	}

	public void registerUser(User user) {
		userRepo.save(user);
		
	}
	
	public User getUserByUsername(String username) {
	    return userRepo.getByUsername(username);
	}

	public boolean verifyLogin(String username, HttpSession session) {
	    return username.equals((String) session.getAttribute("active_user"));
	}

}
