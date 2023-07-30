package com.kk.expensecalculator.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailServiceImpl implements EmailService {

	@Autowired
	JavaMailSender mailSender;
	
	@Override
	public void sendEmail(String to, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		
		mailSender.send(message);

	}

	@Override
	public void sendEmailWithAttachment() throws MessagingException {
		
		MimeMessage msg = mailSender.createMimeMessage();
		// true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        
        helper.setTo("krishnakarthik.adk@gmail.com");

        helper.setSubject("Testing from Spring Boot");

        // default = text/plain
        //helper.setText("Check attachment for image!");

        // true = text/html
        helper.setText("<h1>Expesne Report</h1>", true);

		// hard coded a file path

        helper.addAttachment("Expense_Data.pdf", new File("C:\\projects\\expense-calculator\\Expense_Data.pdf"));

        mailSender.send(msg);
        
        // Delete the file after sending the email.
        Path path = FileSystems.getDefault().getPath("C:\\projects\\expense-calculator\\Expense_Data.pdf");
        try {
			Files.delete(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
