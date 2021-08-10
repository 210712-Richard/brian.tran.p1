package com.revature.beans;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class ReimbursementForm {
	private String firstName;
	private String lastName;
	private String email;
	private LocalDate submitDate;
	private LocalTime submitTime;
	private String location;
	private String description;
	private double cost;
	private String gradingFormat;
	private EventType type;

	
	public ReimbursementForm() {
		super();
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(cost, description, email, firstName, gradingFormat, lastName, location, submitDate,
				submitTime, type);
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
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public String getGradingFormat() {
		return gradingFormat;
	}
	public void setGradingFormat(String gradingFormat) {
		this.gradingFormat = gradingFormat;
	}
	public EventType getType() {
		return type;
	}
	public void setType(EventType type) {
		this.type = type;
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReimbursementForm other = (ReimbursementForm) obj;
		return Double.doubleToLongBits(cost) == Double.doubleToLongBits(other.cost)
				&& Objects.equals(description, other.description) && Objects.equals(email, other.email)
				&& Objects.equals(firstName, other.firstName) && Objects.equals(gradingFormat, other.gradingFormat)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(location, other.location)
				&& Objects.equals(submitDate, other.submitDate) && Objects.equals(submitTime, other.submitTime)
				&& type == other.type;
	}


	@Override
	public String toString() {
		return "ReimbursementForm [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", submitDate=" + submitDate + ", submitTime=" + submitTime + ", location=" + location
				+ ", description=" + description + ", cost=" + cost + ", gradingFormat=" + gradingFormat + ", type="
				+ type + "]";
	}

	
}
