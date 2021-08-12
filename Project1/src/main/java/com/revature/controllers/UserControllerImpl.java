package com.revature.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.beans.User;
import com.revature.beans.UserType;
import com.revature.factory.BeanFactory;
import com.revature.factory.Log;
import com.revature.services.UserService;
import com.revature.services.UserServiceImpl;

import io.javalin.http.Context;

@Log
public class UserControllerImpl implements UserController {

	private static Logger log = LogManager.getLogger(UserController.class);
	private UserService us = (UserService) BeanFactory.getFactory().get(UserService.class, UserServiceImpl.class);

	//private UserDao ud = new UserDao();
	
	public void login(Context ctx) {
		log.trace("Login method called");
		log.debug(ctx.body());
		
		// Try to use a JSON Marshaller to create an object of this type.
		// Javalin does not come with a JSON Marshaller but prefers Jackson. You could also use GSON
		User u = ctx.bodyAsClass(User.class);
		
		// Use the request data to obtain the data requested
		u = us.login(u.getUsername(), u.getPassword());		
		// Create a session if the login was successful
		if(u != null) {
			// Save the user object as loggedUser in the session
			ctx.sessionAttribute("loggedUser", u);
			
			// Try to use the JSON Marshaller to send a JSON string of this object back to the client
			ctx.json(u);
			return;
		}
		
		// Send a 401 is the login was not successful
		ctx.status(401);
	}
	
	public void register(Context ctx) {
		log.trace("Register method called");
		log.debug(ctx.body());
		log.trace("Instantiating user");
		User u = ctx.bodyAsClass(User.class);
		log.trace("UserMade");

		
		//if(us.checkAvailability(u.getUsername())) {
			User newUser = us.register(u.getUsername(), u.getPassword(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getManagerId(), UserType.EMPLOYEE);
			ctx.status(201);
			ctx.json(newUser);
		//} else {
			//ctx.status(409);
			//ctx.html("Username already taken.");
		//}
		
	}
	
	@Override
	public void logout(Context ctx) {
		log.trace("Logout method called");
		log.debug(ctx.body());
		ctx.req.getSession().invalidate();
		ctx.status(204);
	}

}
