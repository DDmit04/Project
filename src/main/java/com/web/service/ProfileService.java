package com.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.data.FriendRequest;
import com.web.data.User;
import com.web.exceptions.UserPasswordException;
import com.web.repository.FriendReqestRepo;
import com.web.repository.UserRepo;
import com.web.utils.DateUtil;

@Service
public class ProfileService {
	
	@Autowired
	private FriendReqestRepo friendReqestRepo;
	
	@Autowired
	private UserRepo userRepo;

	public void addFriendRequest(User user, User currentUser) {
		FriendRequest friendReqest = new FriendRequest(DateUtil.getLocalDate(), currentUser, user);
		friendReqestRepo.save(friendReqest);		
	}

	public Iterable<FriendRequest> findRequestTo(User currentUser) {
		return friendReqestRepo.findByRequestTo(currentUser);
	}

	public Iterable<FriendRequest> findRequestFrom(User currentUser) {
		return friendReqestRepo.findByRequestFrom(currentUser);
	}
	
	public void deleteRequest(FriendRequest frendReqest) {
		friendReqestRepo.delete(frendReqest);		
	}

	public void addFriend(User user, User currentUser, FriendRequest frendReqest) {
		currentUser.getUserFriends().add(user);
		userRepo.save(currentUser);
		user.getUserFriends().add(currentUser);
		userRepo.save(user);
		friendReqestRepo.delete(frendReqest);		
	}

	public void deleteFriend(User user, User currentUser) {
		currentUser.getUserFriends().remove(user);
		userRepo.save(currentUser);	
		user.getUserFriends().remove(currentUser);
		userRepo.save(user);	
	}
	
	public void changePassword(User currentUser, String currentPassword, String newPassword) throws UserPasswordException {
		if(currentPassword.equals(newPassword)) {
			throw new UserPasswordException("new password is " + currentUser.getUsername() + "'s current password!", currentUser);
		}
		if(currentPassword.equals(currentUser.getPassword())) {
			currentUser.setPassword(newPassword);
			userRepo.save(currentUser);
		} else {
			throw new UserPasswordException("Wrong " + currentUser.getUsername() + "'s password!", currentUser);
		}
	}
}