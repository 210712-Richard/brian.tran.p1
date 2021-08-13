package com.revature.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
import com.revature.beans.EventType;
import com.revature.beans.Request;
import com.revature.beans.User;
import com.revature.beans.UserType;
import com.revature.controllers.UserController;
import com.revature.factory.Log;
import com.revature.util.CassandraUtil;

@Log
public class ReimbursementDaoImpl implements ReimbursementDao{
	private CqlSession session = CassandraUtil.getInstance().getSession();
	private static Logger log = LogManager.getLogger(UserController.class);


	@Override
	public void addRequest(UUID eid, Request r) {
		// TODO Auto-generated method stub
		log.trace("Add request called");
		log.trace(eid);
		r.setEid(eid);
		r.setRid(UUID.randomUUID());
		log.trace(r.toString());
		log.trace(r.getReason());



		String query = "Insert into project1.Reimbursement (rid, eid, reimburseAmount, rewardAmount, firstName, lastName, email, submitDate, submitTime, location, description, cost, startDate, gradingFormat, passingGrade, attachment, type, approvedBy, status, reason, isUrgent) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(s)
				.bind(r.getRid(), 
						r.getEid(), 
						r.getReimburseAmount(), 
						r.getRewardAmount(),
						r.getFirstName(), 
						r.getLastName(), 
						r.getEmail(),
						r.getSubmitDate(), 
						r.getSubmitTime(), 
						r.getLocation(), 
						r.getDescription(), 
						r.getCost(), 
						r.getStartDate(), 
						r.getGradingFormat(),
						r.getPassingGrade(), 
						r.getAttachment(), 
						r.getType().toString(), 
						r.getApprovedBy(), 
						r.getStatus(), 
						r.getReason(), 
						r.isUrgent());
		session.execute(bound);
	}

	@Override
	public List<Request> getRequests() {
		// TODO Auto-generated method stub
		String query = "Select rid, eid, reimburseAmount, rewardAmount, firstName, lastName, email, submitDate, submitTime, location, description, cost, startDate, gradingFormat, passingGrade, attachment, type, approvedBy, status, reason, isUrgent from project1.Reimbursement";
		// This query will not be particularly efficient as it needs to query every partition.
		SimpleStatement s = new SimpleStatementBuilder(query).build();
		ResultSet rs = session.execute(s);
		List<Request> requests = new ArrayList<>();
		rs.forEach(row -> {
			Request r = new Request();
			r.setRid(row.getUuid("rid"));
			r.setEid(row.getUuid("eid"));
			r.setReimburseAmount(row.getFloat("reimburseAmount"));
			r.setReimburseAmount(row.getFloat("rewardAmount"));
			r.setFirstName(row.getString("firstname"));
			r.setLastName(row.getString("lastname"));
			r.setEmail(row.getString("email"));
			r.setSubmitDate(row.getLocalDate("submitDate"));
			r.setSubmitTime(row.getLocalTime("submitTime"));
			r.setDescription(row.getString("description"));
			r.setCost(row.getFloat("cost"));
			r.setStartDate(row.getLocalDate("startDate"));
			r.setPassingGrade(row.getString("passingGrade"));
			r.setAttachment(row.getString("attachment"));
			r.setType(EventType.valueOf(row.getString("type")));
			r.setApprovedBy(UserType.valueOf(row.getString("approvedBy")));
			r.setStatus(row.getString("status"));
			r.setReason(row.getString("reason"));
			r.setUrgent(row.getBool("isUrgent"));
			requests.add(r);
		});
		return requests;
	}

	@Override
	public List<Request> getRequest(UUID eid) {
		// TODO Auto-generated method stub
		String query = "Select * from project1.Reimbursement where eid = ? Allow filtering";
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(s).bind(eid);
		ResultSet rs = session.execute(bound);
		List<Request> requests = new ArrayList<>();
		
		if (rs != null) 
		{			
			rs.forEach(row -> {	
				Request r = new Request();				
				r.setRid(row.getUuid("rid"));
				r.setEid(row.getUuid("eid"));
				r.setReimburseAmount(row.getFloat("reimburseAmount"));
				r.setReimburseAmount(row.getFloat("rewardAmount"));
				r.setFirstName(row.getString("firstname"));
				r.setLastName(row.getString("lastname"));
				r.setEmail(row.getString("email"));
				r.setSubmitDate(row.getLocalDate("submitDate"));
				r.setSubmitTime(row.getLocalTime("submitTime"));
				r.setDescription(row.getString("description"));
				r.setCost(row.getFloat("cost"));
				r.setStartDate(row.getLocalDate("startDate"));
				r.setPassingGrade(row.getString("passingGrade"));
				r.setAttachment(row.getString("attachment"));
				r.setType(EventType.valueOf(row.getString("type")));
				if(row.getString("approvedBy") != null) {
					r.setApprovedBy(UserType.valueOf(row.getString("approvedBy")));
				}
				r.setStatus(row.getString("status"));
				r.setReason(row.getString("reason"));
				r.setUrgent(row.getBool("isUrgent"));
				requests.add(r);
			});
		}
		
		return requests;
	}

	@Override
	public void updateRequest(User u, UUID rid, String attachment, String status, String reason, float rewardAmount) {
		log.trace("start");
		List<Request> rs = getRequestByRid(rid);
		Request r = new Request();
		log.trace("here1");
		if(!rs.equals(null)) {
			r = rs.get(0);
			log.trace(r.toString());

		}
		if(attachment == null) {
			attachment = r.getAttachment();
		}
		if(status == null) {
			status = r.getStatus();
		}
		if(reason == null) {
			reason = r.getReason();
		}
		log.trace(rewardAmount);
		if(rewardAmount == 0) {
			rewardAmount = r.getRewardAmount();
		}
		log.trace("here");
		
		String query = "Update project1.Reimbursement set attachment = ?, status = ?, reason = ?, rewardAmount = ?, approvedBy = ? where rid = ?";
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(s).bind(attachment, status, reason, rewardAmount, u.getType(), rid); 
		
		if(status.equals("Approved")) {
			String query2 = "Select * from project1.Reimbursement where rid = ?";
			SimpleStatement s2 = new SimpleStatementBuilder(query2).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
			BoundStatement bound2 = session.prepare(s2).bind(rid); 
			ResultSet rs2 = session.execute(bound2);
			log.trace("hHERNOW");

			Row row = rs2.one();
			log.trace("Past row");

			if(row == null) {
				// if there is no return values
				return;
			}
			log.trace("trace");

			UUID eid = row.getUuid("eid");
			log.trace(eid);

			String query3 = "Select * from project1.Employee where uid = ?";
			SimpleStatement s3 = new SimpleStatementBuilder(query3).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
			BoundStatement bound3 = session.prepare(s3).bind(eid); 
			ResultSet rs3 = session.execute(bound3);
			Row row1 = rs3.one();
			log.trace("Past row");

			if(row1 == null) {
				// if there is no return values
				return;
			}

			User user = new User();
			user.setId(row1.getUuid("uid"));
			user.setAvailableAmount(row1.getFloat("availableAmount"));
			log.trace(user.getId());
			log.trace(user.getAvailableAmount());
			float newAmount = user.getAvailableAmount() - rewardAmount;
			if(newAmount < 0) {
				return;
			}

			String query1 = "Update project1.Employee set availableAmount = ? where uid = ?";
			SimpleStatement s1 = new SimpleStatementBuilder(query1).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
			BoundStatement bound1 = session.prepare(s1).bind(newAmount, user.getId()); 
			session.execute(bound1);
			session.execute(bound);

		}
	}

	@Override
	public List<Request> getUrgents() {
		// TODO Auto-generated method stub
		String query = "Select * from project1.Reimbursement where isUrgent = ? Allow filtering";
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(s).bind(true);
		ResultSet rs = session.execute(bound);
		List<Request> requests = new ArrayList<>();
		rs.forEach(row -> {
			Request r = new Request();
			r.setRid(row.getUuid("rid"));
			r.setEid(row.getUuid("eid"));
			r.setReimburseAmount(row.getFloat("reimburseAmount"));
			r.setReimburseAmount(row.getFloat("rewardAmount"));
			r.setFirstName(row.getString("firstname"));
			r.setLastName(row.getString("lastname"));
			r.setEmail(row.getString("email"));
			r.setSubmitDate(row.getLocalDate("submitDate"));
			r.setSubmitTime(row.getLocalTime("submitTime"));
			r.setDescription(row.getString("description"));
			r.setCost(row.getFloat("cost"));
			r.setStartDate(row.getLocalDate("startDate"));
			r.setPassingGrade(row.getString("passingGrade"));
			r.setAttachment(row.getString("attachment"));
			r.setType(EventType.valueOf(row.getString("type")));
			if(row.getString("approvedBy") != null) { 
				r.setApprovedBy(UserType.valueOf(row.getString("approvedBy")));
			}
			r.setStatus(row.getString("status"));
			r.setReason(row.getString("reason"));
			r.setUrgent(row.getBool("isUrgent"));
			requests.add(r);
		});
		return requests;
	}
	
	public void deleteRequest(UUID rid) {
		String query = "Delete from project1.Reimbursement where rid = ?";
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(s).bind(rid);
		session.execute(bound);		
	}

	
	@Override
	public List<Request> uploadFile(UUID rid, String attachment) {
		

		// TODO Auto-generated method stub
		String query = "Update project1.Reimbursement Set attachment = ? where rid = ?";
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(s).bind(attachment, rid);
		session.execute(bound);
		List<Request> requests = getRequestByRid(rid);
		return requests;
	}

	public List<Request> getRequestByRid(UUID rid){
		String query1 = "Select * from project1.Reimbursement where rid = ?";
		SimpleStatement s1 = new SimpleStatementBuilder(query1).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound1 = session.prepare(s1).bind(rid);
		ResultSet rs = session.execute(bound1);
		List<Request> requests = new ArrayList<>();
		if (rs != null) 
		{			
			rs.forEach(row -> {	
				Request r = new Request();				
				r.setRid(row.getUuid("rid"));
				r.setEid(row.getUuid("eid"));
				r.setReimburseAmount(row.getFloat("reimburseAmount"));
				r.setReimburseAmount(row.getFloat("rewardAmount"));
				r.setFirstName(row.getString("firstname"));
				r.setLastName(row.getString("lastname"));
				r.setEmail(row.getString("email"));
				r.setSubmitDate(row.getLocalDate("submitDate"));
				r.setSubmitTime(row.getLocalTime("submitTime"));
				r.setDescription(row.getString("description"));
				r.setCost(row.getFloat("cost"));
				r.setStartDate(row.getLocalDate("startDate"));
				r.setPassingGrade(row.getString("passingGrade"));
				r.setAttachment(row.getString("attachment"));
				if(row.getString("type") != null) {
					r.setType(EventType.valueOf(row.getString("type")));
				}
				if(row.getString("approvedBy") != null) {
					r.setApprovedBy(UserType.valueOf(row.getString("approvedBy")));
				}
				r.setStatus(row.getString("status"));
				r.setReason(row.getString("reason"));
				r.setUrgent(row.getBool("isUrgent"));
				requests.add(r);
			});
			
		}
		return requests;
	}
}
	
