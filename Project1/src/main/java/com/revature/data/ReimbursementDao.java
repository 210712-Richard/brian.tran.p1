package com.revature.data;

import java.util.List;
import java.util.UUID;

import com.revature.beans.Request;
import com.revature.beans.User;

public interface ReimbursementDao {
	void addRequest(UUID eid, Request r);

	List<Request> getRequests();

	List<Request> getRequest(UUID id);

	void updateRequest(User u, UUID rid, String attachment, String status, String reason, float rewardAmount);
	
	List<Request> getUrgents();
	
	void deleteRequest(UUID rid);
}
