package com.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.data.FriendRequest;
import com.web.data.User;
import com.web.repository.FriendReqestRepo;
import com.web.repository.UserRepo;
import com.web.utils.DateUtil;

@Service
public class ProfileService {
	
	@Autowired
	private FriendReqestRepo friendReqestRepo;
	
	@Autowired
	private UserRepo userRepo;

	public Iterable<FriendRequest> findRequestTo(User currentUser) {
		return friendReqestRepo.findByRequestToId(currentUser.getId());
	}

	public Iterable<FriendRequest> findRequestFrom(User currentUser) {
		return friendReqestRepo.findByRequestFromId(currentUser.getId());
	}
	
	public void addFriendRequest(User user, User currentUser) {
		FriendRequest friendReqest = new FriendRequest(DateUtil.getLocalDate(), currentUser, user);
		friendReqestRepo.save(friendReqest);		
	}
	
	public void deleteFriendRequest(FriendRequest frendReqest) {
		friendReqestRepo.delete(frendReqest);		
	}
	
	public void deleteFriendRequest(User user, User currentUser, FriendRequest frendReqest) {
		Long counterRequest = friendReqestRepo.findOneRequestId(user.getId(), currentUser.getId());
		if(counterRequest != null) {
			friendReqestRepo.deleteById(counterRequest);
		}
		friendReqestRepo.delete(frendReqest);	
	}

	public void addFriend(User user, User currentUser, FriendRequest frendReqest) {
		currentUser.getUserFriends().add(user);
		userRepo.save(currentUser);
		user.getUserFriends().add(currentUser);
		userRepo.save(user);
		deleteFriendRequest(user, currentUser, frendReqest);
	}

	public void deleteFriend(User user, User currentUser) {
		currentUser.getUserFriends().remove(user);
		userRepo.save(currentUser);	
		user.getUserFriends().remove(currentUser);
		userRepo.save(user);	
	}
	
	public void removeSubscription(User currentUser, User user) {
		user.getSubscribers().remove(currentUser);
		userRepo.save(user);
	}

	public void addSubscription(User currentUser, User user) {
		user.getSubscribers().add(currentUser);
		userRepo.save(user);
	}
}