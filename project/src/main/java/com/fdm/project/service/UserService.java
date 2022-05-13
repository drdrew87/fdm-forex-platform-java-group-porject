package com.fdm.project.service;

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

}
