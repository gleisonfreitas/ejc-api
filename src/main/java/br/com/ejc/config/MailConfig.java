package br.com.ejc.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import br.com.ejc.config.property.EjcApiProperty;

@Configuration
public class MailConfig {
	
	@Autowired
	private EjcApiProperty property;
	
	@Bean
	public JavaMailSender javaMailSender() {
		
		Properties properties = new Properties();
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.auth", true);
		properties.put("mail.smtp.starttls.enable", true);
		properties.put("mail.smtp.connectiontimeout", 10000);
		
		JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
		javaMailSenderImpl.setJavaMailProperties(properties);
		javaMailSenderImpl.setHost(property.getMail().getHost());
		javaMailSenderImpl.setPort(property.getMail().getPort());
		javaMailSenderImpl.setUsername(property.getMail().getUsername());
		javaMailSenderImpl.setPassword(property.getMail().getPassword());
		
		return javaMailSenderImpl;		
		
	}

}
