package com.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.api.chat.ChatRoomService;
import com.web.api.chat.ChatSessionService;
import com.web.data.Chat;
import com.web.data.User;
import com.web.repository.ChatRepo;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {
	
	private ChatRepo chatRepo;
	private ChatSessionService chatSessionService;
	
	@Autowired	
	public ChatRoomServiceImpl(ChatRepo chatRepo, ChatSessionServiceImpl chatSessionService) {
		this.chatRepo = chatRepo;
		this.chatSessionService = chatSessionService;
	}

	private boolean userIsAdminOrOwner (User user, User currentUser, Chat chat) {
		return chat.getChatOwner().equals(currentUser) || chat.getChatAdmins().contains(currentUser);
	}
	
	@Override
	public void deleteChatHistory(User user, User currentUser, Chat chat) {
		if (currentUser.equals(user)) {
			if (chat.getChatAdmins().contains(user)) {
				chat.getChatAdmins().remove(user);
			}
			if (chat.getChatMembers().contains(user)) {
				chat.getChatMembers().remove(user);
			}
			chatRepo.save(chat);
		}
	}
	
	@Override
	public void invateUser(User user, Chat chat) {
		chat.getChatMembers().add(user);
		chatRepo.save(chat);	
	}

	@Override
	public void userLeave(User user, User currentUser, Chat chat) {
		if (currentUser.equals(user)) {
			chat.getChatMembers().remove(user);
			chatRepo.save(chat);
		}
	}

	@Override
	public void userReturn(User user, Chat chat) {
		 if(chatSessionService.getSession(user, chat) != null) {
			 chat.getChatMembers().add(user);
			 chatRepo.save(chat);
		 }
	}

	@Override
	public void chaseOutUser(User user, User currentUser, Chat chat) {
		if (userIsAdminOrOwner(user, currentUser, chat)) {
			chat.getChatMembers().remove(user);
			chatRepo.save(chat);
		}		
	}

	@Override
	public void giveAdmin(User user, User currentUser, Chat chat) {
		if(chat.getChatOwner().equals(currentUser)) {
			chat.getChatAdmins().add(user);
			chatRepo.save(chat);
		}		
	}

	@Override
	public void removeAdmin(User user, User currentUser, Chat chat) {
		if(user.equals(currentUser) || chat.getChatOwner().equals(currentUser)) {
			chat.getChatAdmins().remove(user);
			chatRepo.save(chat);
		}		
	}

	@Override
	public void banUser(User user, User currentUser, Chat chat) {
		if(userIsAdminOrOwner(user, currentUser, chat)) {
			chat.getChatBanList().add(user);
			chat.getChatMembers().remove(user);
			chatRepo.save(chat);
		}
	}

	@Override
	public void unbanUser(User user, User currentUser, Chat chat) {
		if(userIsAdminOrOwner(user, currentUser, chat)) {
			chat.getChatBanList().remove(user);
			chatRepo.save(chat);
		}		
	}

}
