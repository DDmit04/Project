package com.forum;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
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
import com.web.data.ChatSession;
import com.web.data.ChatSessionConnection;
import com.web.data.User;
import com.web.repository.ChatSessionRepo;
import com.web.service.ChatSessionConnectionServiceImpl;
import com.web.service.ChatSessionServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
@TestPropertySource(locations="classpath:application.properties") 
public class ChatSessionServiceTest {
	
	@Autowired
	private ChatSessionServiceImpl chatSessionService;
	
	@MockBean
	private ChatSessionRepo chatSessionRepo;
	
	@MockBean
	private ChatSessionConnectionServiceImpl сhatSessionConnectionService;

	@Test
	public void testCreateNewChatSession() {
		User user = new User("1", "1", null);
		Chat chat = new Chat("name", null);
		ChatSessionConnection sessionConnection = new ChatSessionConnection();
		sessionConnection.setId((long) 1);
		doReturn(sessionConnection).when(сhatSessionConnectionService).openNewSessionConnection(Mockito.any());
		ChatSession testSession = chatSessionService.createNewChatSession(user, chat);
		assertNotNull(testSession.getLastConnectionDate());
		assertTrue(testSession.getLastConnectionId() == 1);
		Mockito.verify(chatSessionRepo, Mockito.times(2)).save(Mockito.any());
		Mockito.verify(сhatSessionConnectionService, Mockito.times(1)).openNewSessionConnection(Mockito.any());
	}

	@Test
	public void testDeleteChatSession() {
		User user = new User("1", "1", null);
		Chat chat = new Chat("name", null);
		chatSessionService.deleteChatSession(user, chat);
		Mockito.verify(chatSessionRepo, Mockito.times(1)).findSessionByChatAndUser(Mockito.any(), Mockito.any());
		Mockito.verify(chatSessionRepo, Mockito.times(1)).delete(Mockito.any());
	}

	@Test
	public void testConnectChatSesion() {
		User user = new User("1", "1", null);
		Chat chat = new Chat("name", null);
		ChatSession session = new ChatSession(chat, user);
		ChatSessionConnection sessionConnection = new ChatSessionConnection();
		sessionConnection.setId((long) 1);
		doReturn(session).when(chatSessionRepo).findSessionByChatAndUser(Mockito.any(), Mockito.any());
		doReturn(sessionConnection).when(сhatSessionConnectionService).openNewSessionConnection(Mockito.any());
		ChatSession testSession = chatSessionService.connectChatSesion(user, chat);
		assertTrue(testSession.getLastConnectionId() == 1);
		Mockito.verify(сhatSessionConnectionService, Mockito.times(1)).openNewSessionConnection(Mockito.any());
		Mockito.verify(chatSessionRepo, Mockito.times(1)).save(Mockito.any());
	}

	@Test
	public void testDisconnectChatSession() {
		User user = new User("1", "1", null);
		Chat chat = new Chat("name", null);
		ChatSession session = new ChatSession(chat, user);
		doReturn(session).when(chatSessionRepo).findSessionByChatAndUser(Mockito.any(), Mockito.any());
		chatSessionService.disconnectChatSession(user, chat);
		Mockito.verify(сhatSessionConnectionService, Mockito.times(1)).closeSessionConnection(Mockito.any());
		Mockito.verify(chatSessionRepo, Mockito.times(1)).findSessionByChatAndUser(Mockito.any(), Mockito.any());
		Mockito.verify(chatSessionRepo, Mockito.times(1)).save(Mockito.any());
	}

	@Test
	public void testUpdateLastConnectionDate() {
		User user = new User("1", "1", null);
		Chat chat = new Chat("name", null);
		ChatSession session = new ChatSession(chat, user);
		doReturn(session).when(chatSessionRepo).findSessionByChatAndUser(Mockito.any(), Mockito.any());
		ChatSession testSession = chatSessionService.updateLastConnectionDate(user, chat);
		assertNotNull(testSession.getLastConnectionDate());
		Mockito.verify(chatSessionRepo, Mockito.times(1)).findSessionByChatAndUser(Mockito.any(), Mockito.any());
		Mockito.verify(chatSessionRepo, Mockito.times(1)).save(Mockito.any());
	}

}
