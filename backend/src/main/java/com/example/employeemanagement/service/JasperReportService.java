package com.example.employeemanagement.service;

import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

@Service
public class JasperReportService {

	public byte[] generateReport(String position) throws Exception {
	    System.out.println("Starting report generation for position: " + position);

	    Connection connection = null;
	    try {
	        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_db", "root", "Filz@h135");
	        System.out.println("Database connection established.");

	        String reportPath = "src/main/resources/employeelist.jrxml";
	        JasperReport jasperReport = JasperCompileManager.compileReport(reportPath);
	        System.out.println("Jasper report compiled.");

	        Map<String, Object> parameters = new HashMap<>();
	        parameters.put("POSITION", position == null || position.isEmpty() || position.equalsIgnoreCase("All") ? "%" : position);
	        System.out.println("Parameters set: " + parameters);

	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
	        System.out.println("Report filled successfully.");

	        return JasperExportManager.exportReportToPdf(jasperPrint);
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Error during report generation: " + e.getMessage());
	        throw e;
	    } finally {
	        if (connection != null) {
	            connection.close();
	        }
	    }
	}

}
