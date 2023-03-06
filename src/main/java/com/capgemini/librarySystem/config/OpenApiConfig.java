package com.capgemini.librarySystem.config;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class OpenApiConfig {
  @Bean
  public OpenAPI customConfiguration() {
    return new OpenAPI()
        .components(new Components())
        .info(new Info().title("Books API Docs")
            .description("Books REST API documentation"));
  }
}
