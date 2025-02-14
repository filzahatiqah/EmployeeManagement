package com.example.employeemanagement.repository;  			//Repository package

import com.example.employeemanagement.model.Employee;  			//Import Employee entity
import org.springframework.data.jpa.repository.JpaRepository;  	//Import JpaRepository interface
import org.springframework.data.jpa.repository.Query;  			//For custom JPQL/SQL queries
import org.springframework.data.repository.query.Param;  		//For named parameters in queries
import org.springframework.data.jpa.repository.query.Procedure; //For calling stored procedures
import org.springframework.stereotype.Repository;  				//Marks this interface as a repository component

@Repository  																//Marks this as a repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> { //Extends JpaRepository to inherit CRUD methods

    @Query(value = "CALL UpdateEmployeeSalary(:empId, :newSalary)", nativeQuery = true)  //Native SQL query to call a stored procedure
    void updateSalary(@Param("empId") Long empId, @Param("newSalary") Double newSalary); //Method to update emp salary using stored procedure
    
    @Procedure(name = "AddEmployee")  												//Calls stored procedure named 'AddEmployee'
    void addEmployee(String name, String email, String position, double salary);  	//Method to add emp using stored procedure
}
