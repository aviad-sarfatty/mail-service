package com.example.mailservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mailservice.model.dto.SendBatchMailRequestDTO;
import com.example.mailservice.model.dto.SendMailRequestDTO;
import com.example.mailservice.service.MailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Mail Sending", description = "Endpoints for sending emails")
public class MailController {

	private final MailService mailService;
	
	public MailController(MailService mailService) {
		this.mailService = mailService;
	}
	
	@PostMapping("/send")
	@Operation(summary = "Send a single email")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mail successfully accepted for delivery"),
            @ApiResponse(responseCode = "401", description = "Unauthorized (API key missing or invalid)"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
	public ResponseEntity<String> sendMail(@RequestHeader("X-API-Key") String apiKey,
			@Valid @RequestBody SendMailRequestDTO request, HttpServletRequest httpRequest)
	{
		String requester = (String) httpRequest.getAttribute("requester");
		String requestId = mailService.sendMail(requester, request);
		return ResponseEntity.ok(requestId);
	}
	
	@PostMapping("/batch")
	@Operation(summary = "Send a batch of emails")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Batch successfully accepted for delivery"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    public ResponseEntity<String> sendBatch(@RequestHeader("X-API-Key") String apiKey,
            @Valid @RequestBody SendBatchMailRequestDTO batchRequest, HttpServletRequest httpRequest) 
	{
        String requester = (String) httpRequest.getAttribute("requester");
        String batchId = mailService.sendBatch(requester, batchRequest);
        return ResponseEntity.ok(batchId);
    }
}
