package com.revature.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.beans.Request;
import com.revature.beans.User;
import com.revature.factory.BeanFactory;
import com.revature.factory.Log;
import com.revature.services.RequestService;
import com.revature.services.RequestServiceImpl;
import com.revature.util.S3Util;

import io.javalin.http.Context;

@Log
public class RequestControllerImpl implements RequestController {
	private static Logger log = LogManager.getLogger(UserController.class);
	private RequestService rs = (RequestService) BeanFactory.getFactory().get(RequestService.class, RequestServiceImpl.class);

	@Override
	public void newRequest(Context ctx) {
		log.trace("newRequest method called");
		log.debug(ctx.body());
		String username = ctx.pathParam("username");
		User loggedUser = (User)ctx.sessionAttribute("loggedUser");
		if(loggedUser == null || !loggedUser.getUsername().equalsIgnoreCase(username)) {
			ctx.status(403);
			return;
		}
		Request r = ctx.bodyAsClass(Request.class);
		rs.createRequest(loggedUser.getId(), r);
		ctx.html("You now have a pending reimbursement request for "+ r.getReimburseAmount());

	}
	
	public void updateRequest(Context ctx) {
		log.trace("updateRequest method called");
		log.debug(ctx.body());
		String username = ctx.pathParam("username");
		
		User loggedUser = (User)ctx.sessionAttribute("loggedUser");
		if(loggedUser == null || !loggedUser.getUsername().equalsIgnoreCase(username)) {
			ctx.status(403);
			return;
		}
		Request r = ctx.bodyAsClass(Request.class);
		rs.updateRequest(loggedUser, r.getRid(), r.getAttachment(), r.getStatus(), r.getReason(), r.getRewardAmount());
		ctx.html("You now have a pending reimbursement request for "+ r.getReimburseAmount());

	}
	
	
	public void getRequests(Context ctx) {
		//Gets all outstanding requests
		//For admins
		log.trace("getRequests method called");
		log.debug(ctx.body());
		String username = ctx.pathParam("username");
		User loggedUser = (User)ctx.sessionAttribute("loggedUser");
		if(loggedUser == null || !loggedUser.getUsername().equalsIgnoreCase(username)) {
			ctx.status(403);
			return;
		}
		if(loggedUser.getType().equals("EMPLOYEE")) {
			ctx.status(403);
			ctx.html("You don't have permission to view all requests.");
			return;
		}
		ctx.json(rs.getRequests());
	
	}

	@Override
	public void getRequest(Context ctx) {
		//Gets requests for a specific user
		log.trace("getRequest method called");
		log.debug(ctx.body());
		String username = ctx.pathParam("username");
		User loggedUser = (User)ctx.sessionAttribute("loggedUser");
		if(loggedUser == null || !loggedUser.getUsername().equalsIgnoreCase(username)) {
			ctx.status(403);
			return;
		}
		
		ctx.json(rs.getRequest(loggedUser.getId()));
		
	}

	@Override
	public void cancelRequest(Context ctx) {
		log.trace("cancelRequest method called");
		log.debug(ctx.body());
		String username = ctx.pathParam("username");
		User loggedUser = (User)ctx.sessionAttribute("loggedUser");
		if(loggedUser == null || !loggedUser.getUsername().equalsIgnoreCase(username)) {
			ctx.status(403);
			return;
		}
		
		Request r = ctx.bodyAsClass(Request.class);
		rs.cancel(r.getRid());
		ctx.html("You have canceled your pending request.");
		
	}	
	
	@Override
	public void uploadFile(Context ctx) {
		String filetype = ctx.header("extension");
		String[] alllowFileTypesArray = new String[] {"pdf", "jpg"};		
		List<String> allowFileTypes = new ArrayList<>(Arrays.asList(alllowFileTypesArray));
		
		if (filetype == null || !allowFileTypes.contains(filetype)) {
			ctx.status(400); // bad request, expected the filetype
			return;
		}		

		String key = UUID.randomUUID() + "." + filetype; 
		S3Util.getInstance().uploadToBucket(key, ctx.bodyAsBytes());
	}

}
