package com.example.employeemanagement.config; 								//Config package

import org.springframework.context.annotation.Configuration; 				//Marks as a config class
import org.springframework.web.servlet.config.annotation.CorsRegistry; 		//For adding CORS mappings
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer; 	//Interface for config web MVC

@Configuration 												//Indicates that this is a config class
public class CorsConfig implements WebMvcConfigurer { 		//Class implementing CORS config
	
    @Override 												//Overrides method from WebMvcConfigurer
    public void addCorsMappings(CorsRegistry registry) { 	//Method to config CORS
        registry.addMapping("/**") 							//Allow CORS for all endpoints
                .allowedOrigins("http://localhost:3000") 	//Allow requests from React frontend at this URL
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") //Allowed HTTP methods
                .allowedHeaders("*") 						//Allow all headers
                .allowCredentials(true) 					//Allow sending cookies/authorization headers
                .maxAge(3600);  							//Cache pre-flight requests for 3600 seconds (1 hour)
    }
}
