package com.kk.expensecalculator.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
@PropertySource(value = "classpath:config/mail.properties")
public class EmailServiceImpl implements EmailService {

	@Autowired
	JavaMailSender mailSender;
	
	@Value("${mail.to}") 
	private String to;
	
	@Value("${mail.subject}")
	private String subject;
	
	@Value("${mail.body}")
	private String body;
	
	@Value("${pdf.filename}")
	private String fileName;
	
	@Value("${pdf.generatedfile.location}")
	private String fileLocation;
	
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
        
        helper.setTo(to);
        helper.setSubject(subject);

        // default = text/plain
        //helper.setText("Check attachment for image!");

        // true = text/html
        helper.setText(body, true);

        Path path = FileSystems.getDefault().getPath(fileLocation + "\\" + fileName);
        
        if(Files.exists(path)) {
        	helper.addAttachment(fileName, new File(fileLocation + "\\" + fileName));
        }

        mailSender.send(msg);
        
        // Delete the file after sending the email.
        try {
        	Files.deleteIfExists(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
