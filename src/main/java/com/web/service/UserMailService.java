package com.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;

import com.sun.mail.smtp.SMTPSendFailedException;
import com.web.data.User;
import com.web.exceptions.UserException;
import com.web.exceptions.UserExceptionType;
import com.web.repository.UserRepo;
import com.web.utils.ServiceUtils;

@Service
public class UserMailService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
    private MailService mailService;

    @Value("${spring.mail.username}")
    private String username;
    
    @Value("${hostname}")
	private String hostname;

	
	public void changeEmail(User currentUser, String code, String newEmail) throws UserException, MailSendException, SMTPSendFailedException{
		User user = userRepo.findByEmailChangeCode(code);
		if(user != null && currentUser.equals(user)) {
			currentUser.setUserEmail(newEmail);
			currentUser.setEmailConfirmCode(ServiceUtils.generateRandomKey(4));
			sendMessageToActivate(currentUser);
			userRepo.save(currentUser);
		} else {
			throw new UserException("wrong email change code!", user, UserExceptionType.EMAIL_CHANGE_CODE);
		}
	}
	
	public void sendMessageToActivate(User user) throws MailSendException, SMTPSendFailedException {
		if (user.getUserEmail() != null) {
			String message = String.format(
					"Hello, %s! \n" + "Welcome to my app. Please, visit next link: http://%s/activate/%s",
					user.getUsername(), hostname, user.getEmailConfirmCode());
			mailService.send(user.getUserEmail(), "Activation code", message);
		}
	}
	
	public void sendChangeEmailCode(User currentUser) throws MailSendException, SMTPSendFailedException{
			currentUser.setEmailChangeCode(ServiceUtils.generateRandomKey(2));
			userRepo.save(currentUser);
			String message = String.format(
					"Hello, %s! \n" + " this is your change email code %s use it in: http://%s/" + currentUser.getId() + "/profile/settings",
					currentUser.getUsername(), currentUser.getEmailChangeCode(), hostname);
			mailService.send(currentUser.getUserEmail(), "Change email code", message);
	}
	
	public void activateUser(String code) throws UserException {
		User user = userRepo.findByEmailConfirmCode(code);
		if (user != null) {
			user.setEmailConfirmCode(null);
			userRepo.save(user);
		} else {
			throw new UserException("activation code error", user, UserExceptionType.ACTIVATION_CODE);

		}
	}

}
