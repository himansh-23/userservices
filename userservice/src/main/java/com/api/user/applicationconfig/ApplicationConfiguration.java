package com.api.user.applicationconfig;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfiguration {
	
	@Bean
	public PasswordEncoder getPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	@Bean
	public ModelMapper getModelMapper()
	{
		return new ModelMapper();
	}
	
/*	@Bean
	public Session getSession()
	{
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
		return Session.getInstance(props, auth);
	}*/
	
}
