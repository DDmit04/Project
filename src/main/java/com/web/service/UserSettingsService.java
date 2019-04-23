package com.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sun.mail.smtp.SMTPSendFailedException;
import com.web.data.User;
import com.web.exceptions.UserException;
import com.web.exceptions.UserExceptionType;
import com.web.repository.UserRepo;
import com.web.utils.ServiceUtils;

@Service
public class UserSettingsService extends MailService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder; 
	
	public void changeRecoveredPassword(User user, String newPassword) throws UserException {
		if(passwordEncoder.matches( newPassword, user.getPassword())) {
			throw new UserException("new password is " + user.getUsername() + "'s current password!", user, UserExceptionType.CHANGE_PASSWORD);
		} else {
			newPassword = passwordEncoder.encode(newPassword);
			user.setPassword(newPassword);
			user.setPasswordRecoverCode(null);
			userRepo.save(user);
		}
	}
	
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

	public void changeEmail(User currentUser, String code, String newEmail)
			throws UserException, MailSendException, SMTPSendFailedException {
		User userDb = userRepo.findByEmailChangeCode(code);
		if (userDb != null && !userDb.getUserEmail().contentEquals(newEmail)) {
			sendEmailConfirmCode(userDb, newEmail);
			userDb.setUserEmail(newEmail);
			userRepo.save(userDb);
		}else if(userDb.getUserEmail().contentEquals(newEmail)) {
			throw new UserException("this email is " + userDb.getUsername() + "'s email!", userDb, UserExceptionType.EXISTING_EMAIL);
		} else {
			throw new UserException("wrong email change code!", userDb, UserExceptionType.EMAIL_CHANGE_CODE);
		}
	}
	
	public User sendPasswordRecoverCode(String recoverData) throws UserException, MailSendException, SMTPSendFailedException {
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

	public void checkPasswordRecoverCode(User user, String emailCode) throws UserException {
		User userDb = userRepo.findByPasswordRecoverCode(emailCode);
		if (userDb != null && user.equals(userDb)) {
			userDb.setPasswordRecoverCode(null);
			userRepo.save(userDb);
		} else {
			throw new UserException("wrong password recover code!", user, UserExceptionType.WRONG_PASSWORD_RECOVER_CODE);
		}
	}
	
	public void deleteUser(User currentUser, String accountDeletePassword) throws UserException {
		if(passwordEncoder.matches(accountDeletePassword, currentUser.getPassword())) {
			userRepo.delete(currentUser);
		} else {
			throw new UserException("Wrong " + currentUser.getUsername() + "'s password!", currentUser, UserExceptionType.DELETE_USER);
		}
	}
	
	public void confirmEmail(String code) throws UserException {
		User user = userRepo.findByEmailConfirmCode(code);
		if (user != null) {
			user.setEmailConfirmCode(null);
			userRepo.save(user);
		} else {
			throw new UserException("activation code is outdated or not exists!", user, UserExceptionType.ACTIVATION_CODE);
		}
	}
	
	public void sendPasswordRecoverCode(User user, String email) throws MailSendException, SMTPSendFailedException {
		String code = ServiceUtils.generateRandomKey(3);
		super.sendPasswordRecoverCode(user, email, code);
		setPasswordRecoverCode(user, code);
	}
	
	public void sendEmailConfirmCode(User user, String email) throws MailSendException, SMTPSendFailedException {
		String code = ServiceUtils.generateRandomKey(4);
		super.sendEmailConfirmCode(user, email, code);
		setEmailConfirmCode(user, code);

	}
	
	public void sendChangeEmailCode(User user, String email) throws MailSendException, SMTPSendFailedException {
		String code = ServiceUtils.generateRandomKey(2);
		super.sendChangeEmailCode(user, email, code);
		setEmailChangeCode(user, code);
	}
	
	private void setPasswordRecoverCode(User user, String code) {
		user.setPasswordRecoverCode(code);
		userRepo.save(user);
	}

	private void setEmailChangeCode(User user, String code) {
		user.setEmailChangeCode(code);
		userRepo.save(user);
	}
	
	private void setEmailConfirmCode(User user, String code) {
		user.setEmailConfirmCode(code);
		userRepo.save(user);
	}

}
