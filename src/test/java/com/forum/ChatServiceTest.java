package com.forum;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import com.web.WebApplication;
import com.web.data.Chat;
import com.web.data.ChatSession;
import com.web.data.User;
import com.web.repository.ChatRepo;
import com.web.repository.ImageRepo;
import com.web.service.ChatServiceImpl;
import com.web.service.ChatSessionServiceImpl;
import com.web.service.FileServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
@TestPropertySource(locations="classpath:application.properties") 
public class ChatServiceTest {
	
	@Autowired
	private ChatServiceImpl chatService;
	
	@MockBean
	private ChatRepo chatRepo;
	
	@MockBean
	private ImageRepo imageRepo;
	
	@MockBean
	private ChatSessionServiceImpl chatSessionService;
	
	@MockBean
	private FileServiceImpl fileService;
	
	@MockBean
	private PasswordEncoder passwordEncoder; 
	
	@MockBean
	private MultipartFile multipartFile; 

	@Test
	public void testCreateChatChatMultipartFileUser() throws IllegalStateException, IOException {
		User user = new User("1", "1", null);
		Chat chat = new Chat("name", null);
		MockMultipartFile file = new MockMultipartFile("file", "Hello, World!".getBytes());
		doReturn(true).when(multipartFile).isEmpty();
		Chat testChat = chatService.createChat(chat, user, file);
		assertNotNull(testChat.getChatCreationDate());
		assertNotNull(testChat.getLastMessageDate());
		assertEquals(testChat.getChatOwner(), user);
		assertTrue(testChat.getChatMembers().contains(user));
		assertTrue(testChat.getChatAdmins().contains(user));
		Mockito.verify(chatRepo, Mockito.times(3)).save(Mockito.any());
	}

	@Test
	public void testCreateChatUserUser() {
		User firstUser = new User("1", "1", null);
		User secUser = new User("2", "1", null);
		doReturn(null).when(chatRepo).findByChatName("1");
		Chat testChat = chatService.createChat(firstUser ,secUser);
		assertNotNull(testChat.getChatCreationDate());
		assertNotNull(testChat.getLastMessageDate());
		assertEquals(testChat.getChatOwner(), secUser);
		assertTrue(testChat.getChatMembers().contains(firstUser));
		assertTrue(testChat.getChatMembers().contains(secUser));
		assertTrue(testChat.getChatAdmins().contains(firstUser));
		assertTrue(testChat.getChatAdmins().contains(secUser));
		Mockito.verify(chatRepo, Mockito.times(2)).save(Mockito.any());
		Mockito.verify(chatRepo, Mockito.times(1)).findByChatName(Mockito.any());
	}

	@Test
	public void testDeleteChat() {
		Chat chat = new Chat("name", null);
		User user = new User("1", "1", null);
		chat.setChatOwner(user);
		chatService.deleteChat(chat);
		assertTrue(chat.getSessions().size() == 0);
		assertNull(chat.getChatOwner());
		Mockito.verify(chatRepo, Mockito.times(1)).delete(Mockito.any());
	}
	
	@Test
	public void testDeleteChatFail() {
		Chat chat = new Chat("name", null);
		User user = new User("1", "1", null);
		ChatSession session = new ChatSession(chat, user);
		chat.setChatOwner(user);
		chat.getSessions().add(session);
		chatService.deleteChat(chat);
		assertFalse(chat.getSessions().size() == 0);
		assertNotNull(chat.getChatOwner());
		Mockito.verify(chatRepo, Mockito.times(0)).delete(Mockito.any());
	}

	@Test
	public void testChangeChatOwner() {
		Chat chat = new Chat("name", null);
		User firstUser = new User("1", "1", null);
		User secUser = new User("2", "1", null);
		chat.setChatOwner(firstUser);
		//!!!
		doReturn(true).when(passwordEncoder).matches(Mockito.any(), Mockito.any());
		chatService.changeChatOwner(secUser, firstUser, chat, firstUser.getUsername(), firstUser.getPassword());
		assertEquals(chat.getChatOwner(), secUser);
		Mockito.verify(chatRepo, Mockito.times(1)).save(Mockito.any());
	}
	
	@Test
	public void testChangeChatOwnerFail() {
		Chat chat = new Chat("name", null);
		User firstUser = new User("1", "1", null);
		User secUser = new User("2", "1", null);
		chat.setChatOwner(firstUser);
		//!!!
		doReturn(false).when(passwordEncoder).matches(Mockito.any(), Mockito.any());
		chatService.changeChatOwner(secUser, firstUser, chat, "wrongUsername", "orWrongPassword");
		assertNotEquals(chat.getChatOwner(), secUser);
		Mockito.verify(chatRepo, Mockito.times(0)).save(Mockito.any());
	}

	@Test
	public void testUpdateChatSettings() throws IllegalStateException, IOException {
		Chat chat = new Chat("name", null);
		User user = new User("1", "1", null);
		chat.setChatTitle("title");
		chat.setChatOwner(user);
		chat.getChatAdmins().add(user);
		MockMultipartFile file = new MockMultipartFile("file", "Hello, World!".getBytes());
		doReturn(true).when(multipartFile).isEmpty();
		chatService.updateChatSettings(user, chat, "testName", "testTitle", file);
		assertEquals(chat.getChatName(), "testName");
		assertEquals(chat.getChatTitle(), "testTitle");
		Mockito.verify(chatRepo, Mockito.times(1)).save(Mockito.any());
	}
	
	@Test
	public void testUpdateChatSettingsFail() throws IllegalStateException, IOException {
		Chat chat = new Chat("name", null);
		User user = new User("1", "1", null);
		chat.setChatTitle("title");
		MockMultipartFile file = new MockMultipartFile("file", "Hello, World!".getBytes());
		chatService.updateChatSettings(user, chat, "testName", "testTitle", file);
		assertNotEquals(chat.getChatName(), "testName");
		assertNotEquals(chat.getChatTitle(), "testTitle");
		Mockito.verify(chatRepo, Mockito.times(0)).save(Mockito.any());
	}

}
