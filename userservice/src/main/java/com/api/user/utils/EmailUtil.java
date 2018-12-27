package com.api.user.utils;

import java.io.UnsupportedEncodingException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class EmailUtil {

	public static void sendEmail(Session session, String toEmail, String subject, String body){
		
		try
	    {
	      Message msg = new MimeMessage(session);
	      msg.setFrom(new InternetAddress("no_reply@gmail.com", "NoReply-JD"));
	      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
	      msg.setSubject(subject);
	      msg.setText(body);
	      System.out.println("Message is ready");
    	  Transport.send(msg);  
	      System.out.println("EMail Sent Successfully!!");
	    }
	    catch (MessagingException | UnsupportedEncodingException e) {
	      e.printStackTrace();
	   //   System.exit(1);
	    }
	}
	
}
