package com.capgemini.librarySystem;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages =  "com.capgemini.librarySystem")
@OpenAPIDefinition(info = @Info(title = "Library System API", version = "1.0", description = "library of reference books for engineers"))
public class LibrarySystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibrarySystemApplication.class, args);
	}

}
