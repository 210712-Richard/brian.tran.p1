package com.revature.data;

import java.util.List;
import java.util.UUID;

import com.revature.beans.Request;

public interface ReimbursementDao {
	void addRequest(Request r);

	List<Request> getRequests();

	Request getRequest(UUID id);

	void updateReuqest(Request r);
}
