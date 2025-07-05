package com.example.mailservice.service;

import com.example.mailservice.model.dto.SendBatchMailRequestDTO;
import com.example.mailservice.model.dto.SendMailRequestDTO;

public interface MailService {

    /**
     * Send a single email.
     * @param requester the name of the service requesting the send (owner)
     * @param request DTO with mail details
     * @return request ID for tracking status
     */
    String sendMail(String requester, SendMailRequestDTO request);

    /**
     * Send multiple emails in a batch.
     * @param requester the name of the service requesting the batch send (owner)
     * @param batchRequest DTO with list of mails
     * @return batch request ID for tracking status
     */
    String sendBatch(String requester, SendBatchMailRequestDTO batchRequest);
}