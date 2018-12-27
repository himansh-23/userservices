package com.api.user.services;

import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.api.user.applicationconfig.MailProperties;
import com.api.user.utils.EmailUtil;

@Service
public class MailServicesImpl implements MailServices {

	/*
	 * @Value("${mailproperties.frommail}") String fromMail;
	 * 
	 * @Value("${mailproperties.mailpassword}") String mailPassword;
	 */
	@Autowired
	private MailProperties mailProperties;
	@Override
	public void mailSend(String toEmail,String subject,String body) {
	//	final String fromEmail = "himanshu00096@gmail.com"; //requires valid gmail id
	//	final String password = "himanshu@1996"; // correct password for gmail id
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true"); //enable authentication
		props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
		props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
		props.put("mail.smtp.port", "587"); //TLS Port
		
		javax.mail.Authenticator auth = new javax.mail.Authenticator() 
		{
			protected PasswordAuthentication getPasswordAuthentication() 
			{
			return new PasswordAuthentication(mailProperties.getFrommail(), mailProperties.getMailpassword());
			}
		};
		
		Session session = Session.getInstance(props, auth);
		
		EmailUtil.sendEmail(session, toEmail,subject, body);
	
	}	
}

