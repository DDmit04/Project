package com.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.sun.mail.smtp.SMTPSendFailedException;
import com.web.api.MailService;
import com.web.data.User;

@Service
public class MailServiceImpl implements MailService {
	
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;
    
    @Value("${hostname}")
	private String hostname;
    
    @Autowired
    public MailServiceImpl(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
    public void send(String emailTo, String subject, String message) throws MailSendException, SMTPSendFailedException{
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }
    
    @Override
    public void sendPasswordRecoverCode(User user, String email, String randomCode) throws MailSendException, SMTPSendFailedException {
    	if (user.getUserEmail() != null) {
			String message = String.format(
					"Hello, %s! \n" + "this is your passwordRecover code %s",
					user.getUsername(), randomCode);
			send(email, "Password recover code", message);
		}
	}
	
    @Override
    public void sendEmailConfirmCode(User user, String email, String randomCode) throws MailSendException, SMTPSendFailedException {
		if (user.getUserEmail() != null) {
			String message = String.format(
					"Hello, %s! \n" + "Welcome to my app. Please, visit next link: http://%s/activate/%s",
					user.getUsername(), hostname, randomCode);
			send(email, "Activation code", message);
		}
	}
	
    @Override
    public void sendChangeEmailCode(User user, String email, String randomCode) throws MailSendException, SMTPSendFailedException {
		String message = String.format("Hello, %s! \n" + " this is your change email code %s use it in: http://%s/"
				+ user.getId() + "/profile/settings", user.getUsername(), randomCode, hostname);
		send(email, "Change email code", message);
	}
}
