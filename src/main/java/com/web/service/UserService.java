package com.web.service;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.web.data.User;
import com.web.data.Group;
import com.web.data.UserRoles;
import com.web.data.dto.UserDto;
import com.web.exceptions.UserException;
import com.web.exceptions.UserExceptionType;
import com.web.repository.UserRepo;
import com.web.utils.DateUtil;

@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private FileService fileService;

	public void addUser(User user, MultipartFile userPic) throws UserException, IllegalStateException, IOException {
		User userFromDb = userRepo.findByUsername(user.getUsername());
		if (userFromDb != null) {
			throw new UserException("user with name " + user.getUsername() + " already exists!", user, UserExceptionType.EXISTING_USERNAME);
		} else {
			user.setUserPicName(fileService.uploadFile(userPic, UploadType.USER_PIC));
			user.setRegistrationDate(DateUtil.getLocalDate());
			user.setActive(true);
			user.setRoles(Collections.singleton(UserRoles.USER));
			userRepo.save(user);
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepo.findByUsername(username);
	}

	public UserDto findOneToUser(User currentUser, User user) {
		return  userRepo.findOneUserToUserDto(currentUser, currentUser.getId(), user.getId());
	}
	
	public UserDto findOneUserToList(User user) {
		return  userRepo.findOneUserForListDto(user.getId());
	}
	
	public UserDto findOneUserToGroup(User currentUser, Group group) {
		return userRepo.findOneUserToGroupDto(currentUser.getId(), group);
	}

	public void deleteUser(User currentUser, String accountDeletePassword) throws UserException {
		if(accountDeletePassword.equals(currentUser.getPassword())) {
			userRepo.delete(currentUser);
		} else {
			throw new UserException("Wrong " + currentUser.getUsername() + "'s password!", currentUser, UserExceptionType.DELETE_USER);
		}
	}
	
	public void changePassword(User currentUser, String currentPassword, String newPassword) throws UserException {
		if(currentPassword.equals(newPassword)) {
			throw new UserException("new password is " + currentUser.getUsername() + "'s current password!", currentUser, UserExceptionType.CHANGE_PASSWORD);
		}
		if(currentPassword.equals(currentUser.getPassword())) {
			currentUser.setPassword(newPassword);
			userRepo.save(currentUser);
		} else {
			throw new UserException("Wrong " + currentUser.getUsername() + "'s password!", currentUser, UserExceptionType.CHANGE_PASSWORD);
		}
	}

	public UserDto findOneToStatistic(User currentUser) {
		return userRepo.findOneToStatistic(currentUser.getId());
	}
}