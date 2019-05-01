package com.forum;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailSendException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.sun.mail.smtp.SMTPSendFailedException;
import com.web.WebApplication;
import com.web.data.User;
import com.web.exceptions.UserException;
import com.web.repository.UserRepo;
import com.web.service.UserProfileServiceImpl;
import com.web.service.UserServiceImpl;
import com.web.service.UserSettingsServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
@TestPropertySource(locations="classpath:application.properties") 
public class UserServiceTest {
	
	@Autowired
	private UserServiceImpl userService;
	
	@MockBean
	private PasswordEncoder passwordEncoder; 
	
	@MockBean
	private UserProfileServiceImpl userProfileService;
	
	@MockBean
	private UserSettingsServiceImpl userSettingsService;
	
	@MockBean
	private UserRepo userRepo;

	@Test
	public void testCreateUser() throws MailSendException, SMTPSendFailedException, IllegalStateException, UserException, IOException {
		User user = new User("1", "1", null);
		userService.createUser(user, null);
		Mockito.verify(userProfileService, Mockito.times(1)).uploadUserPic(user, null);
		Mockito.verify(userSettingsService, Mockito.times(1)).realizeSendEmailConfirmCode(user, user.getUserEmail());
		Mockito.verify(userRepo, Mockito.times(1)).save(user);
	}

	@Test
	public void testDeleteUser() throws UserException {
		User user = new User("1", "1", null);
		doReturn(true).when(passwordEncoder).matches("1", user.getPassword());
		userService.deleteUser(user, "1");
		Mockito.verify(passwordEncoder, Mockito.times(1)).matches("1", user.getPassword());
		Mockito.verify(userRepo, Mockito.times(1)).save(user);
		assertTrue(user.isDeleted());
	}

}
