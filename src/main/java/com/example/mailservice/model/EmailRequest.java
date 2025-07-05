package com.example.mailservice.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "email_requests")
public class EmailRequest {

    @Id
    @Column(name = "request_id", nullable = false, length = 36)
    private String requestId;

    @Column(name = "requester", nullable = false)
    private String requester;

    @Column(name = "is_batch", nullable = false)
    private boolean isBatch;

    @OneToMany(mappedBy = "emailRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmailSendStatus> mailStatuses;

    public EmailRequest() {
    }

    public EmailRequest(String requestId, String requester, boolean isBatch, List<EmailSendStatus> mailStatuses) {
        this.requestId = requestId;
        this.requester = requester;
        this.isBatch = isBatch;
        this.mailStatuses = mailStatuses;
    }

    // Getters and Setters

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public boolean isBatch() {
        return isBatch;
    }

    public void setBatch(boolean batch) {
        isBatch = batch;
    }

    public List<EmailSendStatus> getMailStatuses() {
        return mailStatuses;
    }

    public void setMailStatuses(List<EmailSendStatus> mailStatuses) {
        this.mailStatuses = mailStatuses;
        if (mailStatuses != null) {
            for (EmailSendStatus status : mailStatuses) {
                status.setEmailRequest(this);
            }
        }
    }
}