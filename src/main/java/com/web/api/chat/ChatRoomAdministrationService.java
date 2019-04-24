package com.web.api.chat;

import com.web.data.Chat;
import com.web.data.User;

public interface ChatRoomAdministrationService {
	
	void chaseOutUser(User user, User currentUser, Chat chat);

	void giveAdmin(User user, User currentUser, Chat chat);

	void removeAdmin(User user, User currentUser, Chat chat);

	void banUser(User user, User currentUser, Chat chat);

	void unbanUser(User user, User currentUser, Chat chat);

}
