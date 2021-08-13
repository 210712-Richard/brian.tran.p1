package com.revature.beans;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;


public class Request {
	private UUID rid;
	private UUID eid;
	private float reimburseAmount;
	private float rewardAmount;
	private String firstName;
	private String lastName;
	private String email;
	private LocalDate submitDate;
	private LocalTime submitTime;
	private String location;
	private String description;
	private float cost;
	private LocalDate startDate;
	private String gradingFormat;
	private String passingGrade;
	private String attachment;
	private EventType type;
	private UserType approvedBy;
	private String status;
	private String reason;
	private boolean isUrgent;
	
	public Request() {
		super();
	}
	
	public Request(UUID rid, UUID eid, float reimburseAmount, float rewardAmount, String firstName, String lastName, String email, LocalDate submitDate, LocalTime submitTime, String location, String description, 
					float cost, LocalDate startDate, String gradingFormat, String passingGrade, String attachment, EventType type, UserType approvedBy, String status, String reason, boolean isUrgent) {
		this.eid = eid;
		this.rid = rid;
		this.reimburseAmount = reimburseAmount;
		this.rewardAmount = rewardAmount;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.submitDate = submitDate;
		this.submitTime = submitTime;
		this.location = location;
		this.description = description;
		this.cost = cost;
		this.startDate = startDate;
		this.gradingFormat = gradingFormat;
		this.passingGrade = passingGrade;
		this.attachment = attachment;
		this.type = type;
		this.approvedBy = approvedBy;
		this.status = status;
		this.reason = reason;
		this.isUrgent = isUrgent;
	}


	public UUID getRid() {
		return rid;
	}
	public UUID getEid() {
		return eid;
	}
	public void setEid(UUID eid) {
		this.eid = eid;
	}

	public void setRid(UUID rid) {
		this.rid = rid;
	}
	public float getReimburseAmount() {
		return reimburseAmount;
	}
	public void setReimburseAmount(float reimburseAmount) {
		this.reimburseAmount = reimburseAmount;
	}
	public float getRewardAmount() {
		return rewardAmount;
	}
	public void setRewardAmount(float rewardAmount) {
		this.rewardAmount = rewardAmount;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDate getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(LocalDate submitDate) {
		this.submitDate = submitDate;
	}
	public LocalTime getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(LocalTime submitTime) {
		this.submitTime = submitTime;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getCost() {
		return cost;
	}
	public void setCost(float cost) {
		this.cost = cost;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public String getGradingFormat() {
		return gradingFormat;
	}
	public void setGradingFormat(String gradingFormat) {
		this.gradingFormat = gradingFormat;
	}
	public String getPassingGrade() {
		return passingGrade;
	}
	public void setPassingGrade(String passingGrade) {
		this.passingGrade = passingGrade;
	}
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public EventType getType() {
		return type;
	}
	public void setType(EventType type) {
		this.type = type;
	}
	public UserType getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(UserType approvedBy) {
		this.approvedBy = approvedBy;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public boolean isUrgent() {
		return isUrgent;
	}
	public void setUrgent(boolean isUrgent) {
		this.isUrgent = isUrgent;
	}

	@Override
	public String toString() {
		return "Request [rid=" + rid + ", eid=" + eid + ", reimburseAmount=" + reimburseAmount + ", rewardAmount="
				+ rewardAmount + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", submitDate=" + submitDate + ", submitTime=" + submitTime + ", location=" + location
				+ ", description=" + description + ", cost=" + cost + ", startDate=" + startDate + ", gradingFormat="
				+ gradingFormat + ", passingGrade=" + passingGrade + ", attachment=" + attachment + ", type=" + type
				+ ", approvedBy=" + approvedBy + ", status=" + status + ", reason=" + reason + ", isUrgent=" + isUrgent
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(approvedBy, attachment, cost, description, eid, email, firstName, gradingFormat, isUrgent,
				lastName, location, passingGrade, reason, reimburseAmount, rewardAmount, rid, startDate, status,
				submitDate, submitTime, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Request other = (Request) obj;
		return approvedBy == other.approvedBy && Objects.equals(attachment, other.attachment)
				&& Float.floatToIntBits(cost) == Float.floatToIntBits(other.cost)
				&& Objects.equals(description, other.description) && Objects.equals(eid, other.eid)
				&& Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(gradingFormat, other.gradingFormat) && isUrgent == other.isUrgent
				&& Objects.equals(lastName, other.lastName) && Objects.equals(location, other.location)
				&& Objects.equals(passingGrade, other.passingGrade) && Objects.equals(reason, other.reason)
				&& Float.floatToIntBits(reimburseAmount) == Float.floatToIntBits(other.reimburseAmount)
				&& Float.floatToIntBits(rewardAmount) == Float.floatToIntBits(other.rewardAmount)
				&& Objects.equals(rid, other.rid) && Objects.equals(startDate, other.startDate)
				&& Objects.equals(status, other.status) && Objects.equals(submitDate, other.submitDate)
				&& Objects.equals(submitTime, other.submitTime) && type == other.type;
	}


}
