package com.kk.expensecalculator.service;

import jakarta.mail.MessagingException;

public interface EmailService {
	public void sendEmail(String to, String subject, String body);
	
	public void sendEmailWithAttachment() throws MessagingException;
}
