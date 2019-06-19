package com.bitcamp.dto;

public class BoardDTO {
	private int employee_id;
	private String last_name;
	private String email;
	private float salary;
	
	public BoardDTO() {}
	public BoardDTO(int employee_id, String last_name, String email, float salary) {
		this.employee_id = employee_id;
		this.last_name = last_name;
		this.email = email;
		this.salary = salary;
	}
	public int getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public float getSalary() {
		return salary;
	}
	public void setSalary(float salary) {
		this.salary = salary;
	}
	
}
