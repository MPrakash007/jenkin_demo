package com.altimetrik.services;

import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaEmail {

	Properties emailProperties;
	Session mailSession;
	MimeMessage emailMessage;
	static String invoice_no;

	public static void Sendmail() throws AddressException, MessagingException, IOException {
		if (invoice_no != null) {
			JavaEmail javaEmail = new JavaEmail();

			javaEmail.setMailServerProperties();
			javaEmail.createEmailMessage();
			javaEmail.sendEmail();
		} else
			System.out.println("No mail is Present");
		System.out.println("\n____________________________");
	}

	public void setMailServerProperties() {

		String emailPort = "587";// gmail's smtp port

		emailProperties = System.getProperties();
		emailProperties.put("mail.smtp.port", emailPort);
		emailProperties.put("mail.smtp.host", "smtp.gmail.com");
		emailProperties.put("mail.smtp.auth", "true");
		emailProperties.put("mail.smtp.starttls.enable", "true");

	}

	public static void invno(String invoiceNo) {
		invoice_no = invoiceNo;
	}

	public void createEmailMessage() throws AddressException, MessagingException {

		String toEmails =ReceiveEmailWithAttachment.from;
		String emailSubject = "Invoice Approval!";
		String emailBody = "Your invoiceNo"  + invoice_no +  "has been approved";

		mailSession = Session.getInstance(emailProperties, null);
		emailMessage = new MimeMessage(mailSession);

		
			emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmails.toString()));
		

		emailMessage.setSubject(emailSubject);
		emailMessage.setContent(emailBody, "text/html");// for a html email
		// emailMessage.setText(emailBody);// for a text email

	}

	public void sendEmail() throws AddressException, MessagingException, IOException {
		
		Properties properties = new Properties();
		InputStream input = new FileInputStream("C:\\Users\\mprakash\\workspace\\mail-attachments\\properties\\config.properties");
		properties.load(input);

		String emailHost = "smtp.gmail.com";
		String fromUser = properties.getProperty("userName");// just the id alone
														// without @gmail.com
		String fromUserEmailPassword = properties.getProperty("password");

		Transport transport = mailSession.getTransport("smtp");

		transport.connect(emailHost, fromUser, fromUserEmailPassword);

		transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
		transport.close();
		System.out.println("Email sent successfully.");
	
	}

}