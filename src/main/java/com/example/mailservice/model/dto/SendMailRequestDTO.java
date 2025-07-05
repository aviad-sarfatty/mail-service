package com.example.mailservice.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Details of a single email to send")
public class SendMailRequestDTO {

	@NotBlank(message = "Recipient email is required")
    @Email(message = "Invalid email format")
    @Schema(description = "Recipient email address", example = "user@example.com", required = true)
    private String to;

	@NotBlank(message = "Subject is required")
    @Schema(description = "Subject line of the email", example = "Welcome!", required = true)
    private String subject;

	@NotBlank(message = "Body is required")
    @Schema(description = "Email body content", example = "Thank you for registering.", required = true)
    private String body;

    @Schema(description = "Content type: 'text/plain' or 'text/html'", example = "text/plain")
    private String contentType = "text/plain";

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
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

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	} 
}