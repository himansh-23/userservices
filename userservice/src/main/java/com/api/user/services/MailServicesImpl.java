package com.api.user.services;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.springframework.stereotype.Service;

import com.api.user.utils.EmailUtil;
@Service
public class MailServicesImpl implements MailServices {

	@Override
	public void mailSend(String toEmail) {
		final String fromEmail = "himanshu00096@gmail.com"; //requires valid gmail id
		final String password = "himanshu@1996"; // correct password for gmail id
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
		props.put("mail.smtp.port", "587"); //TLS Port
		props.put("mail.smtp.auth", "true"); //enable authentication
		props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
		
		Authenticator auth = new Authenticator() 
		{
			protected PasswordAuthentication getPasswordAuthentication() 
			{
			return new PasswordAuthentication(fromEmail, password);
			}
		};
		
		Session session = Session.getInstance(props, auth);
		EmailUtil.sendEmail(session, toEmail,"For User Verification", "Click On Below Link For registration");
		System.out.println("Mail Sent");
	}	
}

