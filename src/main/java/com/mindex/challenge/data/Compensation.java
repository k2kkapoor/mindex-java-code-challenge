package com.mindex.challenge.data;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Compensation {
	
	/* 
	 * Though Task 2 didn't mention the format for effectivedate.
	 * Have taken String for demonstration purpose but better would be to take calendar object.
	 * */
	
	@DBRef
	private Employee employee;
	private String salary;
	private String effectiveDate;
	
	
	
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	
	
}
