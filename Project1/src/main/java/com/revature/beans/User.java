package com.revature.beans;
import java.util.Objects;
import java.util.UUID;


public class User {
	private UUID id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private UUID managerId;
	private float availableAmount;
	private UserType type;
	private String directSupervisor;
	private String departmentHead;
	private String benCo;
	
	public User() {
		super();
	}
	
	public User(UUID id, String username, String password, String firstName, String lastName, String email, UUID managerId, float availableAmount, UserType type, String directSupervisor, String departmentHead, String benCo) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.managerId = managerId;
		this.availableAmount = availableAmount;
		this.type = type;
		this.directSupervisor = directSupervisor;
		this.departmentHead = departmentHead;
		this.benCo = benCo;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
	
	public UUID getManagerId() {
		return managerId;
	}

	public void setManagerId(UUID managerId) {
		this.managerId = managerId;
	}
	
	public float getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(float availableAmount) {
		this.availableAmount = availableAmount;
	}
	
//	public UserType getType() {
//		return type;
//	}

	public String getType() {
		return type.toString();
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public String getDirectSupervisor() {
		return directSupervisor;
	}

	public void setDirectSupervisor(String directSupervisor) {
		this.directSupervisor = directSupervisor;
	}

	public String getDepartmentHead() {
		return departmentHead;
	}

	public void setDepartmentHead(String departmentHead) {
		this.departmentHead = departmentHead;
	}

	public String getBenCo() {
		return benCo;
	}

	public void setBenCo(String benCo) {
		this.benCo = benCo;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", email=" + email + ", availableAmount="
				+ availableAmount + ", type=" + type + ", directSupervisor=" + directSupervisor + ", departmentHead="
				+ departmentHead + ", benCo=" + benCo + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(availableAmount, benCo, departmentHead, directSupervisor, email, firstName, id, lastName,
				password, type, username, managerId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Double.doubleToLongBits(availableAmount) == Double.doubleToLongBits(other.availableAmount)
				&& Objects.equals(benCo, other.benCo) && Objects.equals(departmentHead, other.departmentHead)
				&& Objects.equals(directSupervisor, other.directSupervisor) && Objects.equals(email, other.email)
				&& Objects.equals(firstName, other.firstName) && Objects.equals(id, other.id)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(password, other.password)
				&& type == other.type && Objects.equals(username, other.username)
				&& Objects.equals(managerId, other.managerId);
	}	
}