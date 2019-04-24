package com.web.api;

import org.springframework.mail.MailSendException;

import com.sun.mail.smtp.SMTPSendFailedException;
import com.web.data.User;
import com.web.exceptions.UserException;

public interface PasswordRecoverService {
	
	User realizeSendPasswordRecoverCode(String recoverData) throws UserException, MailSendException, SMTPSendFailedException;
	
	void sendPasswordRecoverCode(User user, String email) throws MailSendException, SMTPSendFailedException;
	
	void checkPasswordRecoverCode(User user, String emailCode) throws UserException;
	
	void changeRecoveredPassword(User user, String newPassword) throws UserException;

}
