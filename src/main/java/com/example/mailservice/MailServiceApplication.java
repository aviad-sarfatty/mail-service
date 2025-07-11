package com.example.mailservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MailServiceApplication {

    private static final Logger log = LoggerFactory.getLogger(MailServiceApplication.class);

    public static void main(String[] args) {
    	log.info("Starting Email Service Application...");
        SpringApplication.run(MailServiceApplication.class, args);
    }
}