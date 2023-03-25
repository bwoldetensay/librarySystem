package com.capgemini.librarySystem.config;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@OpenAPIDefinition
@Configuration
public class OpenApiConfig {
  @Bean
  public OpenAPI customConfiguration() {
    return new OpenAPI()
        .info(new Info().title("Books API Docs")
            .description("Books REST API documentation"));
  }
}
