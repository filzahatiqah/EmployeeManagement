package com.example.employeemanagement.service;

import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee saveEmployee(Employee employee) {
        // Save or update the employee
        return employeeRepository.save(employee);
    }

    public void updateSalary(Long empId, Double newSalary) {
        employeeRepository.updateSalary(empId, newSalary);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
    
    //Call the Stored Procedure from EmployeeService
    public void addEmployeeUsingSP(String name, String email, String position, double salary) {
        employeeRepository.addEmployee(name, email, position, salary);
    }
}