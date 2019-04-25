package com.web.api.user;

import java.io.IOException;

import org.springframework.mail.MailSendException;
import org.springframework.web.multipart.MultipartFile;

import com.sun.mail.smtp.SMTPSendFailedException;
import com.web.data.Group;
import com.web.data.User;
import com.web.data.dto.UserDto;
import com.web.exceptions.UserException;

public interface UserService {
	
	void createUser(User user, MultipartFile userPic)
			throws UserException, IllegalStateException, IOException, MailSendException, SMTPSendFailedException;
	
	void deleteUser(User user, String currentPassword) throws UserException;

	User getUserByUsername(User user);

	UserDto getOneUserToUser(User user, User currentUser);

	UserDto getOneUserToList(User user);

	UserDto getOneUserToGroup(User user, Group group);

	UserDto getOneUserToStatistic(User user);

}