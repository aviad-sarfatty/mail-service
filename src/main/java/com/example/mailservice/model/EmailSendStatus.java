package com.example.mailservice.model;

import com.example.mailservice.model.enums.EmailStatusEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "email_send_statuses")
public class EmailSendStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email_request_id", nullable = false)
    private EmailRequest emailRequest;

    @Column(name = "to_email", nullable = false)
    private String toEmail;

    @Column(name = "subject")
    private String subject;

    @Column(name = "body", columnDefinition = "TEXT")
    private String body;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EmailStatusEnum status;

    @Column(name = "error_message")
    private String errorMessage;

    public EmailSendStatus() {
    }

    public EmailSendStatus(EmailRequest emailRequest, String toEmail, String subject, String body, EmailStatusEnum status) {
        this.emailRequest = emailRequest;
        this.toEmail = toEmail;
        this.subject = subject;
        this.body = body;
        this.status = status;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public EmailRequest getEmailRequest() {
        return emailRequest;
    }

    public void setEmailRequest(EmailRequest emailRequest) {
        this.emailRequest = emailRequest;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public EmailStatusEnum getStatus() {
        return status;
    }

    public void setStatus(EmailStatusEnum status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}