package com.mindex.challenge.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;

//Task 2

@Service
public class CompensationServiceImpl implements CompensationService {

	private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);
	
	@Autowired
	private CompensationRepository compensationRepository;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Override
	public Compensation read(String id) {
		
		LOG.debug("Reading compensation for employee [{}]",id);
		
		Employee employee = employeeService.read(id);
		Compensation compensation = compensationRepository.findByEmployee(employee);
		
		if(compensation == null) {
			LOG.info("No Compensation found for employee with id: [{}]",employee.getEmployeeId());
			throw new RuntimeException("No Compensation found for employee with id: " + employee.getEmployeeId());
		}
		
		return compensation;
	}
	@Override
	public Compensation create(Compensation compensation) {
		
		LOG.debug("Creating compensation for Employee with Id: [{}]",compensation.getEmployee().getEmployeeId());
		
		
		//Get employee details using employee id from the compensation.
		Employee employee = employeeService.read(compensation.getEmployee().getEmployeeId());
		compensation.setEmployee(employee);
		compensationRepository.insert(compensation);
				
		return compensation;
		
	}
}
