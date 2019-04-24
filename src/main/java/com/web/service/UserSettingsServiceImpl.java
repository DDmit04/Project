package com.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sun.mail.smtp.SMTPSendFailedException;
import com.web.api.user.UserSettingsService;
import com.web.data.User;
import com.web.exceptions.UserException;
import com.web.exceptions.UserExceptionType;
import com.web.repository.UserRepo;
import com.web.utils.ServiceUtils;

@Service
public class UserSettingsServiceImpl implements UserSettingsService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private MailServiceImpl mailService;
	
	@Autowired
	private PasswordEncoder passwordEncoder; 
	
	private void sendEmailConfirmCode(User user, String email) throws MailSendException, SMTPSendFailedException {
		String randomCode = ServiceUtils.generateRandomKey(4);
		mailService.sendEmailConfirmCode(user, email, randomCode);
		user.setEmailConfirmCode(randomCode);
	}
	
	private void sendChangeEmailCode(User user, String email) throws MailSendException, SMTPSendFailedException {
		String randomCode = ServiceUtils.generateRandomKey(2);
		mailService.sendChangeEmailCode(user, email, randomCode);
		user.setEmailChangeCode(randomCode);
	}
	
	@Override
	public void changePassword(User user, String currentPassword, String newPassword) throws UserException {
		if(passwordEncoder.matches(newPassword, user.getPassword())) {
			throw new UserException("new password is " + user.getUsername() + "'s current password!", user, UserExceptionType.CHANGE_PASSWORD);
		}
		if(passwordEncoder.matches(currentPassword, user.getPassword())) {
			newPassword = passwordEncoder.encode(newPassword);
			user.setPassword(newPassword);
			userRepo.save(user);
		} else {
			throw new UserException("Wrong " + user.getUsername() + "'s password!", user, UserExceptionType.CHANGE_PASSWORD);
		}
	}

	@Override
	public void changeEmail(User currentUser, String code, String newEmail)
			throws UserException, MailSendException, SMTPSendFailedException {
		User userDb = userRepo.findByEmailChangeCode(code);
		if (userDb != null) {
			realizeSendEmailConfirmCode(userDb, newEmail);
			userDb.setUserEmail(newEmail);
			userRepo.save(userDb);
		} else {
			throw new UserException("wrong email change code!", userDb, UserExceptionType.EMAIL_CHANGE_CODE);
		}
	}
	
	@Override
	public void realizeSendEmailConfirmCode(User user, String email) throws MailSendException, SMTPSendFailedException, UserException {
		User userDb = userRepo.findByUsernameOrEmail(email);
		if (userDb == null) {
			sendEmailConfirmCode(user, email);
		} else {
			throw new UserException("this email is " + user.getUsername() + "'s email!", user, UserExceptionType.EXISTING_EMAIL);
		}
	}
	
	@Override
	public void realizeSendEmailChangeCode(User user, String email) throws MailSendException, SMTPSendFailedException {
		//add timeout maybe
		sendChangeEmailCode(user, email);
	}
	
	@Override
	public void confirmEmail(String code) throws UserException {
		User user = userRepo.findByEmailConfirmCode(code);
		if (user != null) {
			user.setEmailConfirmCode(null);
			userRepo.save(user);
		} else {
			throw new UserException("activation code is outdated or not exists!", user, UserExceptionType.ACTIVATION_CODE);
		}
	}
	
}
