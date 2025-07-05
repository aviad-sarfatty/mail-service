package com.example.mailservice.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI mailServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Mail Service API")
                        .description("Microservice for sending single and batch emails")
                        .version("v1.0"));
    }
}