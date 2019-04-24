package com.web.service;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sun.mail.smtp.SMTPSendFailedException;
import com.web.data.Group;
import com.web.data.User;
import com.web.data.UserRoles;
import com.web.data.dto.UserDto;
import com.web.exceptions.UserException;
import com.web.exceptions.UserExceptionType;
import com.web.repository.UserRepo;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private UserSettingsService userSettingsService;
	
	@Autowired
	private UserProfileService userProfileService;
	
	@Autowired
	private PasswordEncoder passwordEncoder; 
	
	public void createFullUser(User user, MultipartFile userPic) 
			throws UserException, IllegalStateException, IOException, MailSendException, SMTPSendFailedException {
		User fullUser = createUser(user);
		userProfileService.uploadUserPic(fullUser, userPic);
		userSettingsService.realizeSendEmailConfirmCode(fullUser, fullUser.getUserEmail());
		userRepo.save(fullUser);
	}
	
	public User createUser(User user) throws UserException {
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
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepo.findByUsernameOrEmail(username);
	}

	public UserDto getOneUserToUser(User currentUser, User user) {
		return userRepo.findOneUserToUserDto(currentUser, currentUser.getId(), user.getId());
	}
	
	public UserDto getOneUserToList(User user) {
		return userRepo.findOneUserForListDto(user.getId());
	}
	
	public UserDto getOneUserToGroup(User currentUser, Group group) {
		return userRepo.findOneUserToGroupDto(currentUser.getId(), group);
	}

	public UserDto getOneUserToStatistic(User currentUser) {
		return userRepo.findOneUserToStatistic(currentUser.getId());
	}

}