package com.capgemini.librarySystem;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = {"com.capgemini.librarySystem"})
@OpenAPIDefinition(info = @Info(title = "Library System API", version = "1.0", description = "library of reference books for engineers"))
//@EnableMongoRepositories("com.capgemini.librarySystem.repository")
public class LibrarySystemApplication {
	public static void main(String[] args) {

		SpringApplication.run(LibrarySystemApplication.class, args);

	}

}
