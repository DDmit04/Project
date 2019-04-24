package com.web.api;

import org.springframework.mail.MailSendException;

import com.sun.mail.smtp.SMTPSendFailedException;
import com.web.data.User;

public interface MailService {
	
	void send(String emailTo, String subject, String message) throws MailSendException, SMTPSendFailedException;
    
    void sendPasswordRecoverCode(User user, String email, String randomCode) throws MailSendException, SMTPSendFailedException;
	
    void sendEmailConfirmCode(User user, String email, String randomCode) throws MailSendException, SMTPSendFailedException;
	
    void sendChangeEmailCode(User user, String email, String randomCode) throws MailSendException, SMTPSendFailedException;

}
