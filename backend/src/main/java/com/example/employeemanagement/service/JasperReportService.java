package com.example.employeemanagement.service; //Service package

import net.sf.jasperreports.engine.*;  			//JasperReports library imports
import org.springframework.stereotype.Service;  //Marks this class as a Spring service
import java.sql.Connection;  					//JDBC conn interface
import java.sql.DriverManager;  				//JDBC Driver Manager for conn
import java.util.HashMap;  						//HashMap to store parameters
import java.util.Map;  							//Map interface

@Service  										//Marks this class as a Spring service component
public class JasperReportService {

	public byte[] generateReport(String position) throws Exception {  //Method to generate report, throws exceptions if errors occur
	    System.out.println("Starting report generation for position: " + position);

	    Connection connection = null;  														//Initialize db conn object
	    try {
	        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_db", "root", "Filz@h135");  //Establish db conn
	        System.out.println("Database connection established."); 

	        String reportPath = "src/main/resources/employeelist.jrxml";  					//Path to Jasper report template
	        JasperReport jasperReport = JasperCompileManager.compileReport(reportPath);  	//Compile the .jrxml report
	        System.out.println("Jasper report compiled."); 

	        Map<String, Object> parameters = new HashMap<>();  								//Create map for parameters
	        //Add POSITION parameter to map, using wildcard '%' for "All"
	        parameters.put("POSITION", position == null || position.isEmpty() || position.equalsIgnoreCase("All") ? "%" : position); 
	        System.out.println("Parameters set: " + parameters); 
	        
	        //Fill the report with data from the db
	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);  
	        System.out.println("Report filled successfully."); 

	        return JasperExportManager.exportReportToPdf(jasperPrint);  				//Export report to PDF & return as byte array
	    } catch (Exception e) {  														//Catch exceptions
	        e.printStackTrace();  														//Print error stack trace
	        System.out.println("Error during report generation: " + e.getMessage());  	
	        throw e;  																	//Rethrow the exception
	    } finally {
	        if (connection != null) {  													//If conn exists
	            connection.close();  													//Close the conn
	        }
	    }
	}

}
