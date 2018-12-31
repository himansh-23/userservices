package com.api.user.utils;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {
	public static void sendEmail(String toEmail, String subject, String body){
		
		Properties props = new Properties(); 
		props.put("mail.smtp.auth", "true"); //enable authentication
		props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
		props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
		props.put("mail.smtp.port", "587"); //TLS Port
		
		javax.mail.Authenticator auth = new javax.mail.Authenticator() 
		{
			protected PasswordAuthentication getPasswordAuthentication() 
			{
			return new PasswordAuthentication("dummy12hi@gmail.com", "Dummy@12");
			}
		};
		Session session=Session.getInstance(props, auth);
		try
	    {
	      Message msg = new MimeMessage(session);
	      msg.setFrom(new InternetAddress("no_reply@gmail.com", "NoReply-JD"));
	      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
	      msg.setSubject(subject);
	      //body="<button href="+body+">"+"Click Here"+"</button>";
	      msg.setContent(body, "text/html");
	      System.out.println("Message is ready");
    	  Transport.send(msg);  
	      System.out.println("EMail Sent Successfully!!");
	    }
	    catch (MessagingException | UnsupportedEncodingException e) {
	      e.printStackTrace();
	      System.exit(1);
	    }
	}
	
}
