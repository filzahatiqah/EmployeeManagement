package com.example.employeemanagement.controller; 					//Controller package

import com.example.employeemanagement.model.Employee; 				//Import Employee model
import com.example.employeemanagement.service.EmployeeService; 		//Import EmployeeService for business logic
import com.example.employeemanagement.service.JasperReportService; 	//Import JasperReportService for report generation
import org.springframework.beans.factory.annotation.Autowired; 		//Dependency injection
import org.springframework.http.HttpHeaders; 						//For setting HTTP headers
import org.springframework.http.MediaType; 							//For setting content type in HTTP response
import org.springframework.http.ResponseEntity; 					//Represents HTTP response with status & data
import org.springframework.validation.annotation.Validated; 		//Enables validation in the class
import org.springframework.web.bind.annotation.*; 					//Spring web annotations (Get, Post, Put, Delete)
import jakarta.validation.Valid; 									//Validation for request bodies
import java.util.List; 												//Import List collection

@RestController 										//Marks this class as a REST API controller
@RequestMapping("/api/employees") 						//Base URL for all APIs in this controller
@Validated // Enables validation
@CrossOrigin(origins = "http://localhost:3000") 		//Allows React frontend to comm with this backend
public class EmployeeController {

    @Autowired 											//Injects EmployeeService bean
    private EmployeeService employeeService;

    @Autowired 											//Injects JasperReportService bean
    private JasperReportService jasperReportService;

    @GetMapping 										//Handles HTTP GET requests for all emp
    public List<Employee> getAllEmployees() { 
        return employeeService.getAllEmployees(); 		//Calls service to get all emp
    }

    @GetMapping("/report") 															//Handles HTTP GET requests for report generation
    public ResponseEntity<byte[]> generateReport(@RequestParam String position) { 	//@RequestParam gets query param from URL
        try {
            byte[] report = jasperReportService.generateReport(position); 			//Calls service to generate report
            return ResponseEntity.ok() 												//Returns HTTP 200 response with report PDF
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Employee_Report_" + position + ".pdf") // Sets file dw header
                    .contentType(MediaType.APPLICATION_PDF) 						//Sets content type to PDF
                    .body(report); 													//Attaches report data to response
        } catch (Exception e) { 													//Handles errors during report generation
            return ResponseEntity.status(500).body(null); 							//Returns HTTP 500 error
        }
    }

    @PostMapping 															//Handles HTTP POST requests for adding emp
    public Employee addEmployee(@Valid @RequestBody Employee employee) { 	//@RequestBody gets JSON data from HTTP request
        return employeeService.saveEmployee(employee); 						//Saves new emp
    }

    @PutMapping("/{id}") 																			//Handles HTTP PUT requests for updating emp
    public Employee updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee employee) { 	//@PathVariable gets id from URL
        employee.setId(id); 																		//Sets ID to emp object
        return employeeService.saveEmployee(employee); 												//Updates emp
    }

    @PutMapping("/{id}/salary") 														//Handles HTTP PUT requests for updating salary
    public void updateSalary(@PathVariable Long id, @RequestParam Double newSalary) { 	//@RequestParam gets query param from URL
        employeeService.updateSalary(id, newSalary); 									//Calls service to update salary
    }

    @DeleteMapping("/{id}") 								//Handles HTTP DELETE requests for deleting emp
    public void deleteEmployee(@PathVariable Long id) { 
        employeeService.deleteEmployee(id); 				//Deletes emp by ID
    }
}
