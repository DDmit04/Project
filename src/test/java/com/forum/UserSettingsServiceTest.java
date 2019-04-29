package com.forum;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;

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
import com.web.service.MailServiceImpl;
import com.web.service.UserProfileServiceImpl;
import com.web.service.UserSettingsServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
@TestPropertySource(locations="classpath:application.properties") 
public class UserSettingsServiceTest {
	
	@Autowired
	private UserSettingsServiceImpl userSettingsService;
	
	@MockBean
	private UserProfileServiceImpl userProfileService;
	
	@MockBean
	private PasswordEncoder passwordEncoder; 
	
	@MockBean
	private UserRepo userRepo;
	
	@MockBean
	private MailServiceImpl mailService;
	
	@Test
	public void testChangePassword() throws UserException {
		User user = new User("1", "1", null);
		//is new password is current password
		doReturn(false).when(passwordEncoder).matches("2", user.getPassword());
		//check current password
		doReturn(true).when(passwordEncoder).matches("1", user.getPassword());
		doReturn("2").when(passwordEncoder).encode("2");
		userSettingsService.changePassword(user, "1", "2");
		Mockito.verify(userRepo, Mockito.times(1)).save(user);
		Mockito.verify(passwordEncoder, Mockito.times(2)).matches(Mockito.any(), Mockito.any());
		Mockito.verify(passwordEncoder, Mockito.times(1)).encode("2");
		assertEquals("2", user.getPassword());
	}

	@Test
	public void testChangeEmail() throws MailSendException, SMTPSendFailedException, UserException {
		User user = new User("1", "1", null);
		user.setEmailChangeCode("123");
		doReturn(user).when(userRepo).findByEmailChangeCode("123");
		userSettingsService.changeEmail(user, user.getEmailChangeCode(), "some@some.some");
		Mockito.verify(userRepo, Mockito.times(1)).findByEmailChangeCode("123");
		Mockito.verify(userRepo, Mockito.times(1)).save(user);
		assertEquals(user.getEmailChangeCode(), null);
		assertEquals(user.getUserEmail(), "some@some.some");
	}

	@Test
	public void testRealizeSendEmailConfirmCode() throws MailSendException, SMTPSendFailedException, UserException {
		User user = new User("1", "1", null);
		user.setEmailConfirmCode("123");
		//user email is unique at registration
		doReturn(null).when(userRepo).findByUsernameOrEmail("some@some.some");
		userSettingsService.realizeSendEmailConfirmCode(user, "some@some.some");
		Mockito.verify(mailService, Mockito.times(1)).sendEmailConfirmCode(Mockito.any(), Mockito.any(), Mockito.any());
		Mockito.verify(userRepo, Mockito.times(1)).findByUsernameOrEmail("some@some.some");
		assertNotNull(user.getEmailConfirmCode());
	}

	@Test
	public void testRealizeSendEmailChangeCode() throws MailSendException, SMTPSendFailedException {
		User user = new User("1", "1", null);
		user.setUserEmail("some@some.some");
		userSettingsService.realizeSendEmailChangeCode(user);
		Mockito.verify(mailService, Mockito.times(1)).sendChangeEmailCode(Mockito.any(), Mockito.any(), Mockito.any());
		assertNotNull(user.getEmailChangeCode());
	}

	@Test
	public void testConfirmEmail() throws UserException {
		User user = new User("1", "1", null);
		user.setEmailConfirmCode("123");
		doReturn(user).when(userRepo).findByEmailConfirmCode("123");
		userSettingsService.confirmEmail("123");
		Mockito.verify(userRepo, Mockito.times(1)).save(user);
		Mockito.verify(userRepo, Mockito.times(1)).findByEmailConfirmCode("123");
		assertEquals(user.getEmailConfirmCode(), null);
	}

}
