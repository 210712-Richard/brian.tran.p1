package com.revature.services;

import java.util.UUID;

import com.revature.beans.User;
import com.revature.beans.UserType;

public interface UserService {
	
	User login(String username, String password); 
		
	User register(String username, String password, String firstName, String lastName, String email, UUID managerId, UserType type);
	
	boolean checkAvailability(String newname);

}
