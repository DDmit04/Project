package com.forum;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
import com.web.service.PasswordRecoverServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
@TestPropertySource(locations="classpath:application.properties") 
public class PasswordRecoverServiceTest {
	
	@Autowired
	private PasswordRecoverServiceImpl passwordRecoverService;
	
	@MockBean
	private UserRepo userRepo; 
	
	@MockBean
	private MailServiceImpl mailService;
	
	@MockBean
	private PasswordEncoder passwordEncoder; 

	@Test
	public void testRealizeSendPasswordRecoverCode() throws MailSendException, SMTPSendFailedException, UserException {
		User user = new User("1", "1", null);
		doReturn(user).when(userRepo).findByUsernameOrEmail(Mockito.any());
		User userDb = passwordRecoverService.realizeSendPasswordRecoverCode("123");
		assertNotNull(userDb.getPasswordRecoverCode());
		Mockito.verify(userRepo, Mockito.times(1)).save(user);
		Mockito.verify(userRepo, Mockito.times(1)).findByUsernameOrEmail(Mockito.any());
		Mockito.verify(mailService, Mockito.times(1)).sendPasswordRecoverCode(Mockito.any(), Mockito.any(), Mockito.any());
	}
	
	@Test (expected = UserException.class)
	public void testRealizeSendPasswordRecoverCodeNotActiveEmailFail() throws MailSendException, SMTPSendFailedException, UserException {
		User user = new User("1", "1", null);
		user.setEmailConfirmCode("321");
		doReturn(user).when(userRepo).findByUsernameOrEmail(Mockito.any());
		User userDb = passwordRecoverService.realizeSendPasswordRecoverCode("123");
		assertNull(userDb.getPasswordRecoverCode());
		assertNotNull(userDb.getEmailConfirmCode());
		Mockito.verify(userRepo, Mockito.times(0)).save(user);
		Mockito.verify(userRepo, Mockito.times(1)).findByUsernameOrEmail(Mockito.any());
		Mockito.verify(mailService, Mockito.times(0)).sendPasswordRecoverCode(Mockito.any(), Mockito.any(), Mockito.any());
	}
	
	@Test (expected = UserException.class)
	public void testRealizeSendPasswordRecoverCodeUserExistFail() throws MailSendException, SMTPSendFailedException, UserException {
		User user = new User("1", "1", null);
		doReturn(null).when(userRepo).findByUsernameOrEmail(Mockito.any());
		passwordRecoverService.realizeSendPasswordRecoverCode("123");
		Mockito.verify(userRepo, Mockito.times(0)).save(user);
		Mockito.verify(userRepo, Mockito.times(1)).findByUsernameOrEmail(Mockito.any());
		Mockito.verify(mailService, Mockito.times(0)).sendPasswordRecoverCode(Mockito.any(), Mockito.any(), Mockito.any());	}

	@Test
	public void testCheckPasswordRecoverCode() throws UserException {
		User user = new User("1", "1", null);
		user.setPasswordRecoverCode("123");
		doReturn(user).when(userRepo).findByPasswordRecoverCode(Mockito.any());
		passwordRecoverService.checkPasswordRecoverCode(user, "123");
		Mockito.verify(userRepo, Mockito.times(1)).findByPasswordRecoverCode(Mockito.any());
	}
	
	@Test (expected = UserException.class)
	public void testCheckPasswordRecoverCodeFail() throws UserException {
		User user = new User("1", "1", null);
		user.setPasswordRecoverCode("123");
		doReturn(null).when(userRepo).findByPasswordRecoverCode(Mockito.any());
		passwordRecoverService.checkPasswordRecoverCode(user, "123");
		Mockito.verify(userRepo, Mockito.times(1)).findByPasswordRecoverCode(Mockito.any());
	}

	@Test
	public void testChangeRecoveredPassword() throws UserException {
		User user = new User("1", "1", null);
		user.setPassword("321");
		doReturn(false).when(passwordEncoder).matches("123", user.getPassword());
		doReturn("123").when(passwordEncoder).encode("123");
		passwordRecoverService.changeRecoveredPassword(user, "123");
		assertEquals(user.getPassword(), "123");
		assertNull(user.getPasswordRecoverCode());
		Mockito.verify(userRepo, Mockito.times(1)).save(user);
	}
	
	@Test (expected = UserException.class)
	public void testChangeRecoveredPasswordFail() throws UserException {
		User user = new User("1", "1", null);
		user.setPassword("321");
		doReturn(true).when(passwordEncoder).matches("123", user.getPassword());
		passwordRecoverService.changeRecoveredPassword(user, "123");
		assertNotEquals(user.getPassword(), "123");
		Mockito.verify(userRepo, Mockito.times(0)).save(user);
	}

}
