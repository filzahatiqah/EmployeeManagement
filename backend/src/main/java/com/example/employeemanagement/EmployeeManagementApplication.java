package com.example.employeemanagement;								//Package declaration for project structure

import org.springframework.boot.SpringApplication; 					//Import Spring Boot's main app class
import org.springframework.boot.autoconfigure.SpringBootApplication;//Annotation to auto-configure Spring Boot app

@SpringBootApplication 						//Enables Spring Boot auto-config, component scanning, & config support
public class EmployeeManagementApplication { 								//Main app class

    public static void main(String[] args) { 								//Main method, entry point of the app
        SpringApplication.run(EmployeeManagementApplication.class, args); 	//Starts the Spring Boot app
        System.out.println("Application started!");
    }
}
