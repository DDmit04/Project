package com.forum;

import static org.junit.Assert.*;

import java.time.Clock;
import java.time.LocalDateTime;

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
import com.web.data.ChatSession;
import com.web.data.ChatSessionConnection;
import com.web.data.User;
import com.web.repository.ChatSessionConnectionRepo;
import com.web.service.ChatSessionConnectionServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
@TestPropertySource(locations="classpath:application.properties") 
public class ChatSessionConnectionServiceTest {
	
	@Autowired
	private ChatSessionConnectionServiceImpl chatSessionConnectionService;
	
	@MockBean
	private ChatSessionConnectionRepo chatSessionConnectionRepo;

	@Test
	public void testOpenNewSessionConnection() {
		User user = new User("1", "1", null);
		Chat chat = new Chat("name", null);
		ChatSession session = new ChatSession(chat, user);
		ChatSessionConnection testSessionConnection = chatSessionConnectionService.openNewSessionConnection(session);
		assertNotNull(testSessionConnection.getConnectChatDate());
		assertEquals(testSessionConnection.getSession(), session);
		Mockito.verify(chatSessionConnectionRepo, Mockito.times(1)).save(Mockito.any());
	}

	@Test
	public void testCloseSessionConnection() {
		User user = new User("1", "1", null);
		Chat chat = new Chat("name", null);
		ChatSession session = new ChatSession(chat, user);
		ChatSessionConnection chatSessionConnection = new ChatSessionConnection();
		chatSessionConnection.setConnectChatDate(LocalDateTime.now(Clock.systemUTC()));
		chatSessionConnection.setDisconnectChatDate(LocalDateTime.now(Clock.systemUTC()));
		session.getSessionConnectionDates().add(chatSessionConnection);
		ChatSessionConnection testSessionConnection = chatSessionConnectionService.closeSessionConnection(session);
		assertNotNull(testSessionConnection.getConnectChatDate());
		assertEquals(testSessionConnection.getSession(), session);
		Mockito.verify(chatSessionConnectionRepo, Mockito.times(1)).save(Mockito.any());
	}

}
