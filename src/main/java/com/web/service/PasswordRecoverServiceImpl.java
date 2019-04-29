package com.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sun.mail.smtp.SMTPSendFailedException;
import com.web.api.MailService;
import com.web.api.PasswordRecoverService;
import com.web.data.User;
import com.web.exceptions.UserException;
import com.web.exceptions.UserExceptionType;
import com.web.repository.UserRepo;
import com.web.utils.ServiceUtils;

@Service
public class PasswordRecoverServiceImpl implements PasswordRecoverService {
	
	private UserRepo userRepo; 
	private MailService mailService;
	private PasswordEncoder passwordEncoder; 
	
	@Autowired	
	public PasswordRecoverServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder, MailServiceImpl mailService) {
		this.userRepo = userRepo;
		this.mailService = mailService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User realizeSendPasswordRecoverCode(String recoverData) throws UserException, MailSendException, SMTPSendFailedException {
		User userDb = userRepo.findByUsernameOrEmail(recoverData);
		if (userDb != null) {
			if (userDb.getEmailConfirmCode() == null) {
				sendPasswordRecoverCode(userDb, userDb.getUserEmail());
				userRepo.save(userDb);
			} else {
				throw new UserException("user email in not activated!", UserExceptionType.EMAIL_NOT_ACTIVATED);
			}
		} else {
			throw new UserException("user with this username or email not exists!", UserExceptionType.USER_NOT_fOUND);
		}
		return userDb;
	}
	
	@Override
	public void sendPasswordRecoverCode(User user, String email) throws MailSendException, SMTPSendFailedException {
		String randomCode = ServiceUtils.generateRandomKey(3);
		mailService.sendPasswordRecoverCode(user, email, randomCode);
		user.setPasswordRecoverCode(randomCode);
	}
	
	@Override
	public void checkPasswordRecoverCode(User user, String emailCode) throws UserException {
		User userDb = userRepo.findByPasswordRecoverCode(emailCode);
		if (userDb == null && !user.equals(userDb)) {
			throw new UserException("wrong password recover code!", user, UserExceptionType.WRONG_PASSWORD_RECOVER_CODE);
		}
	}
	
	@Override
	public void changeRecoveredPassword(User user, String newPassword) throws UserException {
		if(passwordEncoder.matches(newPassword, user.getPassword())) {
			throw new UserException("new password is " + user.getUsername() + "'s current password!", user, UserExceptionType.CHANGE_PASSWORD);
		} else {
			newPassword = passwordEncoder.encode(newPassword);
			user.setPassword(newPassword);
			user.setPasswordRecoverCode(null);
			userRepo.save(user);
		}
	}
	
}
