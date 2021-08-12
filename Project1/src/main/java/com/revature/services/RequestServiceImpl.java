package com.revature.services;

import java.util.List;
import java.util.UUID;

import com.revature.beans.Request;
import com.revature.beans.User;
import com.revature.data.ReimbursementDao;
import com.revature.data.ReimbursementDaoImpl;
import com.revature.data.UserDao;
import com.revature.data.UserDaoImpl;
import com.revature.factory.BeanFactory;
import com.revature.factory.Log;

@Log
public class RequestServiceImpl implements RequestService {
	public ReimbursementDao rd = (ReimbursementDao) BeanFactory.getFactory().get(ReimbursementDao.class, ReimbursementDaoImpl.class);

	//Gets all requests for a specific user
	@Override
	public List<Request> getRequest(UUID eid) {
		// TODO Auto-generated method stub
		List<Request> r = rd.getRequest(eid);
		return r;
	}

	//Gets all requests
	@Override
	public List<Request> getRequests() {
		// TODO Auto-generated method stub
		List<Request> r = rd.getRequests();
		return r;
	}
	
	@Override
	public void cancel(UUID rid) {
		// TODO Auto-generated method stub
		rd.deleteRequest(rid);
		
	}

	@Override
	public void createRequest(UUID uid, Request r) {
		// TODO Auto-generated method stub
		rd.addRequest(uid, r);
	}

	@Override
	public void updateRequest(User updateByUser, UUID rid, String attachment, String status, String reason, float rewardAmount) {
		// TODO Auto-generated method stub
		
	}

}
