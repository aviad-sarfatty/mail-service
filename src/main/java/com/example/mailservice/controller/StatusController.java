package com.example.mailservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mailservice.model.dto.StatusResponseDTO;
import com.example.mailservice.service.StatusService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/status")
@Tag(name = "Status", description = "Endpoints for checking mail send status")
public class StatusController {

    private final StatusService statusService;

    public StatusController(StatusService statusService) {
		super();
		this.statusService = statusService;
	}



	@GetMapping("/{requestId}")
    @Operation(summary = "Get status of a single or batch email send request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Requester does not own the request"),
            @ApiResponse(responseCode = "404", description = "Request ID not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized (invalid API key)")
    })
    public ResponseEntity<StatusResponseDTO> getStatus(
            @RequestHeader("X-API-Key") String apiKey,
            @PathVariable String requestId,
            HttpServletRequest httpRequest
    ) {
        String requester = (String) httpRequest.getAttribute("requester");
        StatusResponseDTO response = statusService.getStatusForRequester(requestId, requester);
        return ResponseEntity.ok(response);
    }
}