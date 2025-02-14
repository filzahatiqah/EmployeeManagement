package com.example.employeemanagement.controller;

import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.service.EmployeeService;
import com.example.employeemanagement.service.JasperReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Validated
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JasperReportService jasperReportService;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/report")
    public ResponseEntity<byte[]> generateReport(@RequestParam String position) {
        try {
            byte[] report = jasperReportService.generateReport(position);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Employee_Report_" + position + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(report);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping
    public Employee addEmployee(@Valid @RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee employee) {
        employee.setId(id);
        return employeeService.saveEmployee(employee);
    }

    @PutMapping("/{id}/salary")
    public void updateSalary(@PathVariable Long id, @RequestParam Double newSalary) {
        employeeService.updateSalary(id, newSalary);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }
}
