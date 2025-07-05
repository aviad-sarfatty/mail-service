package com.example.mailservice.model.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Schema(description = "Request to send a batch of emails")
public class SendBatchMailRequestDTO {

    @NotEmpty
    @Valid
    @Schema(description = "List of emails to send", required = true)
    private List<SendMailRequestDTO> mails;

	public List<SendMailRequestDTO> getMails() {
		return mails;
	}

	public void setMails(List<SendMailRequestDTO> mails) {
		this.mails = mails;
	}
}
