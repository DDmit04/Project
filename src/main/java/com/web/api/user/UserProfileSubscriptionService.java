package com.web.api.user;

import com.web.data.User;

public interface UserProfileSubscriptionService {
	
	void removeSubscription(User user, User currentUser);

	void addSubscription(User user, User currentUser);

}
