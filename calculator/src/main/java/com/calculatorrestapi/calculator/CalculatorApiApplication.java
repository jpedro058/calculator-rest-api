package com.calculatorrestapi.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// This annotation tells Spring Boot to scan the packages for components
@SpringBootApplication(scanBasePackages = {"com.calculatorrestapi.calculator", "com.calculatorrestapi.rest"})
public class CalculatorApiApplication {

	// This is the entry point of the application
	public static void main(String[] args) {
		SpringApplication.run(CalculatorApiApplication.class, args);
	}

}
