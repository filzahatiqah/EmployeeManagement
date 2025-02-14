package com.example.employeemanagement.repository;

import com.example.employeemanagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = "CALL UpdateEmployeeSalary(:empId, :newSalary)", nativeQuery = true)
    void updateSalary(@Param("empId") Long empId, @Param("newSalary") Double newSalary);
    
    //to call the stored procedure:
    @Procedure(name = "AddEmployee")
    void addEmployee(String name, String email, String position, double salary);
}