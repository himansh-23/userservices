package com.api.user.applicationconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;
import lombok.Setter;

@Configuration
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "mail.properties")
@Setter @Getter
public class MailProperties {

	String frommail;
	String mailpassword;
	String body;
	String subject;

	/*
	 * public String getFrommail() { return frommail; }
	 * 
	 * public void setFrommail(String frommail) { this.frommail = frommail; }
	 * 
	 * public String getMailpassword() { return mailpassword; }
	 * 
	 * public void setMailpassword(String mailpassword) { this.mailpassword =
	 * mailpassword; }
	 * 
	 * public String getBody() { return body; }
	 * 
	 * public void setBody(String body) { this.body = body; }
	 * 
	 * public String getSubject() { return subject; }
	 * 
	 * public void setSubject(String subject) { this.subject = subject; }
	 */
}
