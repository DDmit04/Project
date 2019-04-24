package com.web.api.user;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.web.data.User;
import com.web.data.dto.FriendRequestDto;

public interface UserProfileService extends UserProfileFriendService, UserProfileSubscriptionService, UserProfileBanService {

	void updateUserProfile(User user, MultipartFile file, String userInformation, String userTitle) throws IllegalStateException, IOException;

	void uploadUserPic(User user, MultipartFile userPic) throws IllegalStateException, IOException;

	Iterable<FriendRequestDto> getFriendRequestsToUser(User currentUser);

	Iterable<FriendRequestDto> getFriendRequestsFromUser(User currentUser);

}