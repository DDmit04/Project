package com.web.api.user;

import com.web.data.FriendRequest;
import com.web.data.User;

public interface UserProfileFriendService {
	
	void createFriendRequest(User user, User currentUser);

	void deleteFriendRequest(FriendRequest frendReqest);

	void deleteFriendRequest(User user, User currentUser, FriendRequest frendReqest);

	void addFriend(FriendRequest frendRequest);

	void deleteFriend(User user, User currentUser);

}
