package com.example.mailservice.service;

import com.example.mailservice.model.dto.StatusResponseDTO;

public interface StatusService {

    /**
     * Get the status of a single or batch request by ID, only if owned by requester.
     * @param requestId the unique ID of the mail send or batch send
     * @param requester the service owner
     * @return DTO with detailed status information
     */
    StatusResponseDTO getStatusForRequester(String requestId, String requester);
}