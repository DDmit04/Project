package com.web.api.user;

import com.web.data.User;

public interface UserProfileBanService {
	
	void addInBlackList(User user, User currentUser);
	
	void removeFromBlackList(User user, User currentUser);

}
