package com.example.employeemanagement.service;  						// Service package

import com.example.employeemanagement.model.Employee;  					//Import Employee model
import com.example.employeemanagement.repository.EmployeeRepository;  	//Import EmployeeRepository
import org.springframework.beans.factory.annotation.Autowired;  		//Dependency injection
import org.springframework.stereotype.Service;  						//Marks this class as a Spring service
import java.util.List;  												//Import List collection

@Service  													//Marks this class as a Spring service
public class EmployeeService {

    @Autowired  											//Injects EmployeeRepository bean
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {  				//Method to get all emp
        return employeeRepository.findAll();  				//Calls JPA repository method to get all emp
    }

    public Employee saveEmployee(Employee employee) {  		//Method to save or update emp
        return employeeRepository.save(employee);  			//Calls JPA repository method to save emp
    }

    public void updateSalary(Long empId, Double newSalary) {	//Method to update salary using stored procedure
        employeeRepository.updateSalary(empId, newSalary);  	//Calls repository method
    }

    public void deleteEmployee(Long id) {  						//Method to delete emp by ID
        employeeRepository.deleteById(id);  					//Calls JPA repository method to delete emp
    }
    
 //Method to add emp using stored procedure
    public void addEmployeeUsingSP(String name, String email, String position, double salary) {  
        employeeRepository.addEmployee(name, email, position, salary);  							//Calls repository method
    }
}
