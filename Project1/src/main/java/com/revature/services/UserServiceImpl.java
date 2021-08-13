package com.revature.services;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.beans.User;
import com.revature.beans.UserType;
import com.revature.controllers.UserController;
import com.revature.data.UserDao;
import com.revature.data.UserDaoImpl;
import com.revature.factory.BeanFactory;
import com.revature.factory.Log;

@Log
public class UserServiceImpl implements UserService {
	public UserDao ud = (UserDao) BeanFactory.getFactory().get(UserDao.class, UserDaoImpl.class);
	private static Logger log = LogManager.getLogger(UserController.class);

	@Override
	public User login(String username, String password) {
		// TODO Auto-generated method stub
		log.trace("Get User for login method called");
		User u = ud.getUser(username);
		log.trace("Called ud");
		if(u.getPassword().equals(password)){
			return u;
		}
		return null;
	}

	@Override
	public User register(String username, String password, String firstName, String lastName, String email, UUID managerId, UserType type) {
		
		User u = new User(UUID.randomUUID(), username, password, firstName, lastName, email, managerId, 1000, UserType.EMPLOYEE, "Susan", "Henry", "Ben");
		log.trace("Adding user to Dao method called");

		ud.addUser(u);
		return u;
	}
	
	public boolean checkAvailability(String newName) {
		return ud.getUsers()
				.stream()
				.noneMatch((u)->u.getUsername().equals(newName));
	}

}
