package com.revature.services;

import java.util.List;
import java.util.UUID;

import com.revature.beans.Request;
import com.revature.beans.User;

public interface RequestService {
	
	void createRequest(UUID uid, Request r);

	List<Request> getRequest(UUID eid);
	
	List<Request> getRequests();	
	
	void cancel(UUID rid);
	
	void updateRequest(User updateByUser, UUID rid, String attachment, String status, String reason, float rewardAmount);
	
	List<Request> getUrgents();	
	
	List<Request> uploadFile(UUID rid, String attachment);

	List<Request> getRequestByRid(UUID rid);
}
