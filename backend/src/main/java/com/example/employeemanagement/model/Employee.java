package com.example.employeemanagement.model; 	//Model package

import jakarta.persistence.*; 					//JPA annotations for database
import jakarta.validation.constraints.Email; 	//Validation for email format
import jakarta.validation.constraints.Min; 		//Validation for minimum value
import jakarta.validation.constraints.NotNull; 	//Validation for non-null fields
import lombok.Data; 							//Lombok for getters, setters, toString, equals, hashCode

@Entity 								//Marks this class as a JPA entity mapped to a db table
@Data 									//Lombok annotation for generating boilerplate code like getters/setters
public class Employee {

    @Id 												//Marks field as primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto-generates ID values
    private Long id; 	

    @NotNull(message = "Name is required") 			//Validates that name is not null
    @Column(nullable = false) 						//Makes column non-null in database
    private String name; // Employee name

    @NotNull(message = "Email is required") 		//Validates email is not null
    @Email(message = "Invalid email format") 		//Validates correct email format
    @Column(nullable = false, unique = true) 		//Non-null and unique constraint in db
    private String email; // Employee email

    private String position; // Employee position

    @Min(value = 0, message = "Salary must be a positive value") // Ensures salary is non-negative
    private double salary; // Employee salary
}
