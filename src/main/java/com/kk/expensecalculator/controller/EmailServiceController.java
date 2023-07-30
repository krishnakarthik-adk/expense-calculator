package com.kk.expensecalculator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kk.expensecalculator.service.EmailService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping(value = "/api/v1")
public class EmailServiceController {
	
	@Autowired
	EmailService emailService;
	
	@GetMapping(value = "/sendEmail")
	public void sendEmail() {
		emailService.sendEmail("krishnakarthik.adk@gmail.com", "Test", "Test");
	}
	
	@GetMapping(value = "/sendEmailWithAttachment")
	public void sendEmailWithAttachment() throws MessagingException {
		emailService.sendEmailWithAttachment();
	}
}
