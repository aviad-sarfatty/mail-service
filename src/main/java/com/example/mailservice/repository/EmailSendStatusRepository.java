package com.example.mailservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mailservice.model.EmailSendStatus;

@Repository
public interface EmailSendStatusRepository extends JpaRepository<EmailSendStatus, Long> {
    // No custom methods needed for now
}