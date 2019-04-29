package com.forum;

import static org.junit.Assert.assertFalse;
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
import com.web.data.User;
import com.web.repository.ChatRepo;
import com.web.service.ChatRoomServiceImpl;
import com.web.service.ChatSessionServiceImpl;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
@TestPropertySource(locations="classpath:application.properties") 
public class ChatRoomServiceTest {
	
	@Autowired
	private ChatRoomServiceImpl chatRoomService;
	
	@MockBean
	private ChatRepo chatRepo;
	
	@MockBean
	private ChatSessionServiceImpl chatSessionService;

	@Test
	public void testDeleteChatHistory() {
		User user = new User("1", "1", null);
		Chat chat = new Chat("name", null);
		chat.getChatMembers().add(user);
		chat.getChatAdmins().add(user);
		chatRoomService.deleteChatHistory(user, user, chat);
		assertFalse(chat.getChatAdmins().contains(user));
		assertFalse(chat.getChatMembers().contains(user));
		Mockito.verify(chatRepo, Mockito.times(1)).save(Mockito.any());
	}
	
	@Test
	public void testDeleteChatHistoryFail() {
		User user = new User("1", "1", null);
		User differentUser = new User("2", "1", null);
		Chat chat = new Chat("name", null);
		chat.getChatMembers().add(user);
		chat.getChatAdmins().add(user);
		chatRoomService.deleteChatHistory(user, differentUser, chat);
		assertTrue(chat.getChatAdmins().contains(user));
		assertTrue(chat.getChatMembers().contains(user));
		Mockito.verify(chatRepo, Mockito.times(0)).save(Mockito.any());
	}

	@Test
	public void testInvateUser() {
		User user = new User("1", "1", null);
		Chat chat = new Chat("name", null);
		chatRoomService.invateUser(user, chat);
		assertTrue(chat.getChatMembers().contains(user));
		Mockito.verify(chatRepo, Mockito.times(1)).save(Mockito.any());
	}

	@Test
	public void testUserLeave() {
		User user = new User("1", "1", null);
		Chat chat = new Chat("name", null);
		chatRoomService.userLeave(user, user, chat);
		assertFalse(chat.getChatMembers().contains(user));
		Mockito.verify(chatRepo, Mockito.times(1)).save(Mockito.any());
	}
	
	@Test
	public void testUserLeaveFail() {
		User user = new User("1", "1", null);
		User differentUser = new User("2", "1", null);
		Chat chat = new Chat("name", null);
		chat.getChatMembers().add(user);
		chatRoomService.userLeave(user, differentUser, chat);
		assertTrue(chat.getChatMembers().contains(user));
		Mockito.verify(chatRepo, Mockito.times(0)).save(Mockito.any());
	}

	@Test
	public void testUserReturn() {
		User user = new User("1", "1", null);
		Chat chat = new Chat("name", null);
		ChatSession session = new ChatSession(chat, user);
		//!!!
		doReturn(session).when(chatSessionService).getSession(user, chat);
		chatRoomService.userReturn(user, chat);
		assertTrue(chat.getChatMembers().contains(user));
		Mockito.verify(chatRepo, Mockito.times(1)).save(Mockito.any());
	}
	
	@Test
	public void testUserReturnFail() {
		User user = new User("1", "1", null);
		Chat chat = new Chat("name", null);
		//!!!
		doReturn(null).when(chatSessionService).getSession(user, chat);
		chatRoomService.userReturn(user, chat);
		assertFalse(chat.getChatMembers().contains(user));
		Mockito.verify(chatRepo, Mockito.times(0)).save(Mockito.any());
	}

	@Test
	public void testChaseOutUser() {
		User chatOwner = new User("1", "1", null);
		User chatMember = new User("1", "1", null);
		Chat chat = new Chat("name", null);
		chat.getChatMembers().add(chatMember);
		chat.setChatOwner(chatOwner);
		chatRoomService.chaseOutUser(chatMember, chatOwner, chat);
		assertFalse(chat.getChatMembers().contains(chatMember));
		Mockito.verify(chatRepo, Mockito.times(1)).save(Mockito.any());
	}
	
	@Test
	public void testChaseOutUserFail() {
		User chatOwner = new User("1", "1", null);
		User userWithoutRights = new User("2", "1", null);
		User chatMember = new User("3", "1", null);
		Chat chat = new Chat("name", null);
		chat.setChatOwner(chatOwner);
		chat.getChatMembers().add(chatMember);
		chatRoomService.chaseOutUser(chatMember, userWithoutRights, chat);
		assertTrue(chat.getChatMembers().contains(chatMember));
		Mockito.verify(chatRepo, Mockito.times(0)).save(Mockito.any());
	}

	@Test
	public void testGiveAdmin() {
		User chatOwner = new User("1", "1", null);
		User chatMember = new User("1", "1", null);
		Chat chat = new Chat("name", null);
		chat.getChatMembers().add(chatMember);
		chat.setChatOwner(chatOwner);
		chatRoomService.giveAdmin(chatMember, chatOwner, chat);
		assertTrue(chat.getChatAdmins().contains(chatMember));
		Mockito.verify(chatRepo, Mockito.times(1)).save(Mockito.any());
	}
	
	@Test
	public void testGiveAdminFail() {
		User chatOwner = new User("1", "1", null);
		User userWithoutRights = new User("2", "1", null);
		User chatMember = new User("3", "1", null);
		Chat chat = new Chat("name", null);
		chat.getChatMembers().add(chatMember);
		chat.setChatOwner(chatOwner);
		chatRoomService.giveAdmin(chatMember, userWithoutRights, chat);
		assertFalse(chat.getChatAdmins().contains(chatMember));
		Mockito.verify(chatRepo, Mockito.times(0)).save(Mockito.any());
	}
	
	@Test
	public void testRemoveAdmin() {
		User chatOwner = new User("1", "1", null);
		User chatAdmin = new User("2", "1", null);
		Chat chat = new Chat("name", null);
		chat.getChatAdmins().add(chatAdmin);
		chat.setChatOwner(chatOwner);
		chatRoomService.removeAdmin(chatAdmin, chatOwner, chat);
		assertFalse(chat.getChatAdmins().contains(chatAdmin));
		Mockito.verify(chatRepo, Mockito.times(1)).save(Mockito.any());
	}
	
	@Test
	public void testRemoveAdminFail() {
		User userWithoutRights = new User("1", "1", null);
		User chatOwner = new User("2", "1", null);
		Chat chat = new Chat("name", null);
		chat.getChatAdmins().add(chatOwner);
		chat.setChatOwner(chatOwner);
		chatRoomService.removeAdmin(chatOwner, userWithoutRights, chat);
		assertTrue(chat.getChatAdmins().contains(chatOwner));
		Mockito.verify(chatRepo, Mockito.times(0)).save(Mockito.any());
	}

	@Test
	public void testBanUser() {
		User chatOwner = new User("1", "1", null);
		User chatMember = new User("2", "1", null);
		Chat chat = new Chat("name", null);
		chat.setChatOwner(chatOwner);
		chatRoomService.banUser(chatMember, chatOwner, chat);
		assertTrue(chat.getChatBanList().contains(chatMember));
		assertFalse(chat.getChatMembers().contains(chatMember));
		Mockito.verify(chatRepo, Mockito.times(1)).save(Mockito.any());
	}
	
	@Test
	public void testBanUserFail() {
		User chatOwner = new User("1", "1", null);
		User userWithoutRights = new User("2", "1", null);
		User chatMember = new User("3", "1", null);
		Chat chat = new Chat("name", null);
		chat.setChatOwner(chatOwner);
		chat.getChatMembers().add(chatMember);
		chatRoomService.banUser(chatMember, userWithoutRights, chat);
		assertFalse(chat.getChatBanList().contains(chatMember));
		assertTrue(chat.getChatMembers().contains(chatMember));
		Mockito.verify(chatRepo, Mockito.times(0)).save(Mockito.any());
	}
	
	@Test
	public void testUnbanUser() {
		User chatOwner = new User("1", "1", null);
		User banedUser = new User("2", "1", null);
		Chat chat = new Chat("name", null);
		chat.setChatOwner(chatOwner);
		chat.getChatBanList().add(banedUser);
		chatRoomService.unbanUser(banedUser, chatOwner, chat);
		assertFalse(chat.getChatBanList().contains(banedUser));
		Mockito.verify(chatRepo, Mockito.times(1)).save(Mockito.any());
	}
	
	@Test
	public void testUnbanUserFail() {
		User chatOwner = new User("1", "1", null);
		User userWithoutRights = new User("2", "1", null);
		User bannedUser = new User("3", "1", null);
		Chat chat = new Chat("name", null);
		chat.setChatOwner(chatOwner);
		chat.getChatBanList().add(bannedUser);
		chatRoomService.unbanUser(bannedUser, userWithoutRights, chat);
		assertTrue(chat.getChatBanList().contains(bannedUser));
		Mockito.verify(chatRepo, Mockito.times(0)).save(Mockito.any());
	}

}
