package com.revature.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
import com.revature.factory.Log;
import com.revature.util.CassandraUtil;

@Log
public class ReimbursementDaoImpl implements ReimbursementDao{
	private CqlSession session = CassandraUtil.getInstance().getSession();

	@Override
	public void addRequest(UUID eid, Request r) {
		// TODO Auto-generated method stub
		String query = "Insert into project1.Reimbursement (rid, eid, reimburseAmount, rewardAmount, firstName, lastName, email, submitDate, submitTime, location, description, cost, startDate, gradingFormat, passingGrade, attachment, type, approvedBy, status, reason, isUrgent) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(s)
				.bind(r.getRid(), eid, r.getReimburseAmount(), r.getRewardAmount(), r.getFirstName(), r.getLastName(), r.getEmail(),
						r.getSubmitDate(), r.getSubmitTime(), r.getLocation(), r.getDescription(), r.getCost(), r.getStartDate(), r.getGradingFormat(),
						r.getPassingGrade(), r.getAttachment(), r.getType(), r.getApprovedBy(), r.getStatus(), r.getReason(), r.isUrgent());
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
	public List<Request> getRequest(UUID rid) {
		// TODO Auto-generated method stub
		String query = "Select * from project1.Reimbursement where rid = ?";
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(s).bind(rid);
		ResultSet rs = session.execute(bound);
		List<Request> requests = new ArrayList<>();
		
		if (rs != null) 
		{			
			rs.forEach(row -> {	
				Request r = null;				
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
		}
		
		return requests;
	}

	@Override
	public void updateRequest(User updateByUser, UUID rid, String attachment, String status, String reason, float rewardAmount) {
		// user can update attachment
		// user can update status --> Deny, Approve, Pending --> only benco, supervisor or direct manager can update
		// user can update rewardamount --> only when status is approved
		
		
		boolean canApprove = false;
		String currentStatus = null;
		
		//Get existing reimbursement record by rid
		String reimbQuery = "Select eid, status from project1.reimbursement where rid = ?";
		String empQuery = "Select managerId from project1.employee where uid = ?";
		
		
		if(updateByUser.getType().equalsIgnoreCase(UserType.HEAD.toString()) ||
				updateByUser.getType().equalsIgnoreCase(UserType.BENCO.toString())) {
			canApprove = true;
		}
		else {
		
			UUID managerId = null;
			
			SimpleStatement reimburseStmt = new SimpleStatementBuilder(reimbQuery).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
			BoundStatement reimburseBound = session.prepare(reimburseStmt).bind(rid);
			ResultSet rsReimburse = session.execute(reimburseBound);
			
			if (rsReimburse != null) {
				
				Row reimburseRow = rsReimburse.one();
				currentStatus = reimburseRow.getString("status");
				
				SimpleStatement empStmt = new SimpleStatementBuilder(empQuery).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
				BoundStatement empBound = session.prepare(empStmt).bind(reimburseRow.getUuid("eid"));
				ResultSet rsEmp = session.execute(empBound);
				
				if (rsEmp != null) {
					Row empRow = rsEmp.one();
					managerId = empRow.getUuid("managerId");
					if (managerId == updateByUser.getId()) {
						canApprove = true;
					}
				}
			}
		}		
		
		List<String> parameters = Arrays.asList();
		StringBuilder sb = new StringBuilder();
		sb.append("Update project1.reimbursement Set ");
		boolean update = false;
		
		if (attachment != null && !attachment.isEmpty()) {			
			parameters.add("attachment = " + attachment);
			update = true;
		}			
	
		if (status != null && status.isEmpty() && canApprove) {
			if (status == "Denied" && (reason == null || reason.isEmpty())){
				update = false;
			}
			else {
				parameters.add("status = " + status);	
				update = true;
			}
		}
		
		if (rewardAmount > 0 && canApprove && (status == "Approved" || currentStatus == "Approved")) {
			parameters.add("rewardAmount = " + rewardAmount);		
			update = true;
		}
		
		if (parameters.size() > 0)
			sb.append(String.join(", ", parameters));
		
		sb.append("Where rid = ?");
		
		if (update) {
			SimpleStatement reimburseUpdateStmt = new SimpleStatementBuilder(sb.toString()).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
			BoundStatement reimburseUpdateBound = session.prepare(reimburseUpdateStmt).bind(rid);
			session.execute(reimburseUpdateBound);
		}
		else
			System.out.println("Access denied");
	}
	

	@Override
	public List<Request> getUrgents() {
		// TODO Auto-generated method stub
		String query = "Select * from Reimbursement where isUrgent = ?";
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
			r.setApprovedBy(UserType.valueOf(row.getString("approvedBy")));
			r.setStatus(row.getString("status"));
			r.setReason(row.getString("reason"));
			r.setUrgent(row.getBool("isUrgent"));
			requests.add(r);
		});
		return requests;
	}
	
	public void deleteRequest(UUID rid) {
		String query = "Delete * from Reimburesement where rid = ?";
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(s).bind(rid);
		session.execute(bound);		
	}

}
