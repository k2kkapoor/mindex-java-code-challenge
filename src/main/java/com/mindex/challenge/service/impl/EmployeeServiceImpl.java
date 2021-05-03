package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    
    private HashSet<String> uniqueEmployeeID = new HashSet<String>();

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Reading employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }
    
    //Task 1 Getting the number of distinct reporting employee.
    
    @Override
	public ReportingStructure reports(String id) {
    	LOG.debug("Finding reporting employee count for ID: [{}]", id);
    	
    	Employee employee = read(id);
    	int numberOfReports = getNumberOfReports(employee);
    	
    	ReportingStructure report = new ReportingStructure(employee, numberOfReports);
    	
		return report;
	}

	public int getNumberOfReports(Employee employee) {
		
		int numberOfReports = 0;
		List<Employee> employeeReports = employee.getDirectReports();
		
		
		//If no reporting Employees
		if(employeeReports == null) {
			return 0;
		}
		
		
		for(Employee emp : employeeReports) {
			
			/* 1. Storing unique ID so that no person is counted more than once in the reporting count. For the case 
			 * 
			 * 
			 *   	 			 John Lennon
                				/           \
         			Paul McCartney         Ringo Starr
                 			/              /        \
            		   Pete Best        Pete Best     George Harrison
            
            	Here Pete Best would be counted once for John Lennon's reporting employees 
            	
            	2. We have to keep cycle in check such as if George Harrison has John Lennon as direct report then it would lead to
            	a cycle.
			 */
			 
			uniqueEmployeeID.add(emp.getEmployeeId());
			try {
				
				// Calling the getNumberOfReports function to get number of reports for each reporting ID.
				getNumberOfReports(read(emp.getEmployeeId()));
				
			}catch(RuntimeException exception){
				
				//In case the Employee ID doesn't exist.
				LOG.info("Database does not contain EmployeeID: [{}]", emp.getEmployeeId());
			}
		}
		numberOfReports = uniqueEmployeeID.size();
		return numberOfReports;
	}


	
}
