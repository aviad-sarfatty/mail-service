package com.example.mailservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mailservice.model.EmailRequest;

@Repository
public interface EmailRequestRepository extends JpaRepository<EmailRequest, String> {
    // requestId is the primary key (String)
}