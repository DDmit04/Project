package com.web.service;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Collections;

import org.springframework.mail.MailSendException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sun.mail.smtp.SMTPSendFailedException;
import com.web.api.user.UserProfileService;
import com.web.api.user.UserService;
import com.web.api.user.UserSettingsService;
import com.web.data.Group;
import com.web.data.User;
import com.web.data.UserRoles;
import com.web.data.dto.UserDto;
import com.web.exceptions.UserException;
import com.web.exceptions.UserExceptionType;
import com.web.repository.UserRepo;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
	
	private UserRepo userRepo;
	private UserSettingsService userSettingsService;
	private UserProfileService userProfileService;
	private PasswordEncoder passwordEncoder; 
	
	public UserServiceImpl(UserRepo userRepo,  PasswordEncoder passwordEncoder, UserSettingsServiceImpl userSettingsService,
			UserProfileServiceImpl userProfileService) {
		this.userRepo = userRepo;
		this.userSettingsService = userSettingsService;
		this.userProfileService = userProfileService;
		this.passwordEncoder = passwordEncoder;
	}

	private User createUser(User user) throws UserException {
		User userFromDbUsername = userRepo.findByUsernameOrEmail(user.getUsername());
		User userFronDbEmail = userRepo.findByUsernameOrEmail(user.getUserEmail());
		if(userFromDbUsername != null) {
			throw new UserException("user with name " + user.getUsername() + " already exists!", user, UserExceptionType.EXISTING_USERNAME);
		}
		else if(userFronDbEmail != null) {
			throw new UserException("user with email " + user.getUserEmail() + " already exists!", user, UserExceptionType.EXISTING_EMAIL);
		} else {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setRegistrationDate(LocalDateTime.now(Clock.systemUTC()));
			user.setActive(true);
			user.setRoles(Collections.singleton(UserRoles.USER));
		}
		return user;
	}
	
	@Override
	public void createUser(User user, MultipartFile userPic) 
			throws UserException, IllegalStateException, IOException, MailSendException, SMTPSendFailedException {
		User fullUser = createUser(user);
		userProfileService.uploadUserPic(fullUser, userPic);
		userSettingsService.realizeSendEmailConfirmCode(fullUser, fullUser.getUserEmail());
		userRepo.save(fullUser);
	}
	
	@Override
	public void deleteUser(User currentUser, String accountDeletePassword) throws UserException {
		if(passwordEncoder.matches(accountDeletePassword, currentUser.getPassword())) {
			userRepo.delete(currentUser);
		} else {
			throw new UserException("Wrong " + currentUser.getUsername() + "'s password!", currentUser, UserExceptionType.DELETE_USER);
		}
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepo.findByUsernameOrEmail(username);
	}
	
	@Override
	public User getUserByUsername(User currentUser) {
		return userRepo.findByUsernameOrEmail(currentUser.getUsername());
	}

	@Override
	public UserDto getOneUserToUser(User user, User currentUser) {
		return userRepo.findOneUserToUser(currentUser, currentUser.getId(), user.getId());
	}
	
	@Override
	public UserDto getOneUserToList(User user) {
		return userRepo.findOneUserToList(user.getId());
	}
	
	@Override
	public UserDto getOneUserToGroup(User currentUser, Group group) {
		return userRepo.findOneUserToGroup(currentUser.getId(), group);
	}

	@Override
	public UserDto getOneUserToStatistic(User currentUser) {
		return userRepo.findOneUserToStatistic(currentUser.getId());
	}

}