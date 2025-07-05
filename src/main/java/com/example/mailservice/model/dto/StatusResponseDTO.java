package com.example.mailservice.model.dto;

import java.util.List;

import com.example.mailservice.model.enums.BatchStatusEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Status response of a single or batch mail send request")
public class StatusResponseDTO {

	@Schema(description = "True if this is a batch request")
    private boolean isBatch;
	
	@Schema(description = "The request ID returned when sending")
    private String requestId;
	
	@Schema(description = "Batch delivery status summary (null for single requests)")
    private BatchStatusEnum batchStatus; 
	
	@Schema(description = "List of individual email statuses")
    private List<MailStatusResultDTO> results;

	public boolean isBatch() {
		return isBatch;
	}

	public void setBatch(boolean isBatch) {
		this.isBatch = isBatch;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public BatchStatusEnum getBatchStatus() {
		return batchStatus;
	}

	public void setBatchStatus(BatchStatusEnum batchStatus) {
		this.batchStatus = batchStatus;
	}

	public List<MailStatusResultDTO> getResults() {
		return results;
	}

	public void setResults(List<MailStatusResultDTO> results) {
		this.results = results;
	} 
}