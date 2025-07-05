package com.example.mailservice.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailSenderConfig {

    @Value("${mail.gmail.host}")
    private String gmailHost;

    @Value("${mail.gmail.port}")
    private int gmailPort;

    @Value("${mail.gmail.username}")
    private String gmailUsername;

    @Value("${mail.gmail.password}")
    private String gmailPassword;

    @Value("${mail.exchange.host}")
    private String exchangeHost;

    @Value("${mail.exchange.port}")
    private int exchangePort;

    @Value("${mail.exchange.username}")
    private String exchangeUsername;

    @Value("${mail.exchange.password}")
    private String exchangePassword;

    @Value("${spring.mail.selected}")
    private String selectedMailSender;

    @Bean(name = "gmailMailSender")
    public JavaMailSender gmailMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(gmailHost);
        mailSender.setPort(gmailPort);
        mailSender.setUsername(gmailUsername);
        mailSender.setPassword(gmailPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", gmailHost);

        return mailSender;
    }

    @Bean(name = "exchangeMailSender")
    public JavaMailSender exchangeMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(exchangeHost);
        mailSender.setPort(exchangePort);
        mailSender.setUsername(exchangeUsername);
        mailSender.setPassword(exchangePassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", exchangeHost);

        return mailSender;
    }

    /**
     * Main mail sender bean selected by property.
     */
    @Bean
    public JavaMailSender mailSender(
        @Qualifier("gmailMailSender") JavaMailSender gmailMailSender,
        @Qualifier("exchangeMailSender") JavaMailSender exchangeMailSender) {

        if ("gmail".equalsIgnoreCase(selectedMailSender)) {
            return gmailMailSender;
        } else if ("exchange".equalsIgnoreCase(selectedMailSender)) {
            return exchangeMailSender;
        } else {
            throw new IllegalArgumentException("Invalid spring.mail.selected value: " + selectedMailSender);
        }
    }
}