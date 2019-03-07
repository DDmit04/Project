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
import com.web.data.UserRoles;
import com.web.data.dto.UserDto;
import com.web.exceptions.UserException;
import com.web.exceptions.UserPasswordException;
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
			throw new UserException("user with name " + user.getUsername() + " already exists!", user);
		} else {
			user.setUserPicName(fileService.uploadFile(userPic, UploadType.USERPIC));
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

	public UserDto findOneUser(User currentUser, User user) {
		return  userRepo.findOneUser(currentUser, currentUser.getId(), user.getId());
	}

	public void deleteUser(User currentUser, String currentPassword) throws UserPasswordException {
		if(currentPassword.equals(currentUser.getPassword())) {
			userRepo.delete(currentUser);
		} else {
			throw new UserPasswordException("Wrong " + currentUser.getUsername() + "'s password!", currentUser);
		}
	}
}
