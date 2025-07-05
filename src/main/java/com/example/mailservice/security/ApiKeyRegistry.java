package com.example.mailservice.security;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "mailservice")
@Getter
@Setter
public class ApiKeyRegistry {

    /**
     * Map of service name → API key loaded from configuration.
     */
    private Map<String, String> apiKeys;

    /**
     * Lookup service name by API key.
     *
     * @param apiKey the API key from the request header
     * @return service name if found; null otherwise
     */
    public String getServiceNameByApiKey(String apiKey) {
        if (apiKey == null || apiKey.isBlank()) {
            return null;
        }
        return apiKeys.entrySet().stream()
                .filter(entry -> apiKey.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }
}