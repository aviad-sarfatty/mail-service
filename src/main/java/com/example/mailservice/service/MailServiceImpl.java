package com.example.mailservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.mailservice.model.EmailRequest;
import com.example.mailservice.model.EmailSendStatus;
import com.example.mailservice.model.dto.SendBatchMailRequestDTO;
import com.example.mailservice.model.dto.SendMailRequestDTO;
import com.example.mailservice.model.enums.EmailStatusEnum;
import com.example.mailservice.repository.EmailRequestRepository;
import com.example.mailservice.repository.EmailSendStatusRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MailServiceImpl implements MailService {

    private final EmailRequestRepository emailRequestRepository;
    private final EmailSendStatusRepository emailSendStatusRepository;
    private final JavaMailSender mailSender;
    
    private static final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);

    public MailServiceImpl(EmailRequestRepository emailRequestRepository,
			EmailSendStatusRepository emailSendStatusRepository, JavaMailSender mailSender) {
		super();
		this.emailRequestRepository = emailRequestRepository;
		this.emailSendStatusRepository = emailSendStatusRepository;
		this.mailSender = mailSender;
	}

	@Override
    @Transactional
    public String sendMail(String requester, SendMailRequestDTO request) {
        String requestId = UUID.randomUUID().toString();

        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setRequestId(requestId);
        emailRequest.setRequester(requester);
        emailRequest.setBatch(false);

        EmailSendStatus status = createSendStatus(emailRequest, request);
        emailRequest.setMailStatuses(List.of(status));

        emailRequestRepository.save(emailRequest);

        // Async or sync send (sync here for simplicity)
        sendEmail(status);

        return requestId;
    }

	@Override
	@Transactional
	public String sendBatch(String requester, SendBatchMailRequestDTO batchRequest) {
	    String batchRequestId = UUID.randomUUID().toString();

	    EmailRequest emailRequest = new EmailRequest();
	    emailRequest.setRequestId(batchRequestId);
	    emailRequest.setRequester(requester);
	    emailRequest.setBatch(true);

	    List<EmailSendStatus> statuses = new ArrayList<>();

	    for (SendMailRequestDTO mailDTO : batchRequest.getMails()) {
	        EmailSendStatus status = createSendStatus(emailRequest, mailDTO);
	        statuses.add(status);
	    }
	    emailRequest.setMailStatuses(statuses);

	    emailRequestRepository.save(emailRequest);

	    // Send each email (sync here, but you can delegate async)
	    statuses.forEach(this::sendEmail);

	    return batchRequestId;
	}

    private EmailSendStatus createSendStatus(EmailRequest emailRequest, SendMailRequestDTO dto) {
    	EmailSendStatus status = new EmailSendStatus();
        status.setEmailRequest(emailRequest);
        status.setToEmail(dto.getTo());
        status.setSubject(dto.getSubject());
        status.setBody(dto.getBody());
        status.setStatus(EmailStatusEnum.PENDING);
        return status;
    }

    private void sendEmail(EmailSendStatus status) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(status.getToEmail());
            message.setSubject(status.getSubject());
            message.setText(status.getBody());
            mailSender.send(message);

            status.setStatus(EmailStatusEnum.SENT);
            status.setErrorMessage(null);
        } catch (Exception e) {
            log.error("Failed to send email to {}", status.getToEmail(), e);
            status.setStatus(EmailStatusEnum.FAILED);
            status.setErrorMessage(e.getMessage());
        }

        // Update status after sending
        emailSendStatusRepository.save(status);
    }
}