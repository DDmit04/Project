package com.web.api.user;

import org.springframework.mail.MailSendException;

import com.sun.mail.smtp.SMTPSendFailedException;
import com.web.data.User;
import com.web.exceptions.UserException;

public interface UserSettingsService {

	void changePassword(User user, String currentPassword, String newPassword) throws UserException;

	void changeEmail(User user, String code, String newEmail)throws UserException, MailSendException, SMTPSendFailedException;

	void realizeSendEmailConfirmCode(User user, String email)throws MailSendException, SMTPSendFailedException, UserException;

	void realizeSendEmailChangeCode(User user, String email) throws MailSendException, SMTPSendFailedException;

	void confirmEmail(String code) throws UserException;

}