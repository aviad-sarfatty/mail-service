package com.example.mailservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.mailservice.exception.ForbiddenException;
import com.example.mailservice.exception.NotFoundException;
import com.example.mailservice.model.EmailRequest;
import com.example.mailservice.model.EmailSendStatus;
import com.example.mailservice.model.dto.MailStatusResultDTO;
import com.example.mailservice.model.dto.StatusResponseDTO;
import com.example.mailservice.model.enums.BatchStatusEnum;
import com.example.mailservice.model.enums.EmailStatusEnum;
import com.example.mailservice.repository.EmailRequestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {

    private final EmailRequestRepository emailRequestRepository;

    public StatusServiceImpl(EmailRequestRepository emailRequestRepository) {
		super();
		this.emailRequestRepository = emailRequestRepository;
	}

	@Override
    public StatusResponseDTO getStatusForRequester(String requestId, String requester) {
        EmailRequest emailRequest = emailRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Request ID not found: " + requestId));

        // Security check: only owner can access status
        if (!emailRequest.getRequester().equals(requester)) {
            throw new ForbiddenException("You are not authorized to view this status");
        }

        List<MailStatusResultDTO> results = emailRequest.getMailStatuses().stream()
                .map(this::mapToMailStatusResultDTO)
                .collect(Collectors.toList());

        StatusResponseDTO responseDTO = new StatusResponseDTO();
        responseDTO.setRequestId(requestId);
        responseDTO.setBatch(emailRequest.isBatch());
        responseDTO.setResults(results);
        
        if (emailRequest.isBatch()) {
            responseDTO.setBatchStatus(calculateBatchStatus(results));
        } else {
            responseDTO.setBatchStatus(null);
        }

        return responseDTO;
    }

    private MailStatusResultDTO mapToMailStatusResultDTO(EmailSendStatus status) {
    	MailStatusResultDTO mailStatusResultDTO = new MailStatusResultDTO();
    	mailStatusResultDTO.setMessageId(status.getId().toString());
    	mailStatusResultDTO.setTo(status.getToEmail());
    	mailStatusResultDTO.setStatus(status.getStatus());
    	mailStatusResultDTO.setErrorMessage(status.getErrorMessage());
    	return mailStatusResultDTO;
    }

    private BatchStatusEnum calculateBatchStatus(List<MailStatusResultDTO> results) {
        boolean allSent = results.stream().allMatch(r -> r.getStatus() == EmailStatusEnum.SENT);
        boolean allFailed = results.stream().allMatch(r -> r.getStatus() == EmailStatusEnum.FAILED);
        if (allSent) {
            return BatchStatusEnum.SUCCESS;
        } else if (allFailed) {
            return BatchStatusEnum.FAILURE;
        } else {
            return BatchStatusEnum.PARTIAL;
        }
    }
}