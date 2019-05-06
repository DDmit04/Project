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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.web.WebApplication;
import com.web.data.Chat;
import com.web.data.Message;
import com.web.data.MessageJson;
import com.web.data.User;
import com.web.repository.ChatRepo;
import com.web.repository.MessageRepo;
import com.web.repository.UserRepo;
import com.web.service.MessageServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
@TestPropertySource(locations="classpath:application.properties") 
public class MessageServiceTest {
	
	@Autowired
	private MessageServiceImpl messageService;
	
	@MockBean
	private MessageRepo messageRepo;
	
	@MockBean
	private ChatRepo chatRepo;
	
	@MockBean
	private UserRepo userRepo;

	@Test
	public void testCreateMessage() {
		User user = new User("1", "1", null);
		Chat chat = new Chat("1", null);
		MessageJson Jmessage = new MessageJson((long) 1, "message", "sender", null, null, null);
		doReturn(user).when(userRepo).findByUsernameOrEmail(Mockito.any());
		doReturn(chat).when(chatRepo).findChatById(Mockito.any());
		Message message = messageService.createMessage((long) 1, Jmessage);
		assertNotNull(message.getMessageDate());
		assertEquals(message.getMessageAuthor(), user);
		assertEquals(message.getMessageChat(), chat);
		Mockito.verify(chatRepo, Mockito.times(1)).findChatById(Mockito.any());
		Mockito.verify(userRepo, Mockito.times(1)).findByUsernameOrEmail(Mockito.any());
		Mockito.verify(chatRepo, Mockito.times(1)).save(Mockito.any());
		Mockito.verify(messageRepo, Mockito.times(1)).save(Mockito.any());
	}

}
