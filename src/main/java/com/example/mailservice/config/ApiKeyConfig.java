package com.example.mailservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "mailservice")
@Getter
@Setter
public class ApiKeyConfig {
    private Map<String, String> apiKeys;
}