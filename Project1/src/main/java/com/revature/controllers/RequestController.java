package com.revature.controllers;

import io.javalin.http.Context;

public interface RequestController {

	void newRequest(Context ctx);
	
	void updateRequest(Context ctx);

	void getRequest(Context ctx);

	void cancelRequest(Context ctx);

	void uploadFile(Context ctx);
	
	void getUrgents(Context ctx);
	
	void getFile(Context ctx);
}
