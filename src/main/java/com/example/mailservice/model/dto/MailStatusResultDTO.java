package com.example.mailservice.model.dto;

import com.example.mailservice.model.enums.EmailStatusEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Status of an individual email")
public class MailStatusResultDTO {

	@Schema(description = "Message ID assigned to the email", example = "msg-123")
    private String messageId;
	
	@Schema(description = "Recipient email", example = "user@example.com")
    private String to;
	
	@Schema(description = "Delivery status of the email", example = "SENT")
    private EmailStatusEnum status;
	
	@Schema(description = "Error message in case of failure", example = "SMTP timeout")
    private String errorMessage;

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
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