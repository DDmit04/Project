package com.web.api.chat;

import com.web.data.Chat;
import com.web.data.User;

public interface ChatRoomService extends ChatRoomAdministrationService{
	
	void deleteChatHistory(User user, User currentUser, Chat chat);
	
	void invateUser(User user, Chat chat);

	void userLeave(User user, User currentUser, Chat chat);

	void userReturn(User user, Chat chat);

}
