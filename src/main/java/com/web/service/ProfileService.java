package com.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.data.FriendRequest;
import com.web.data.User;
import com.web.repository.FriendRequestRepo;
import com.web.repository.UserRepo;
import com.web.utils.DateUtil;

@Service
public class ProfileService {
	
	@Autowired
	private FriendRequestRepo friendRequestRepo;
	
	@Autowired
	private UserRepo userRepo;

	public Iterable<FriendRequest> findRequestTo(User currentUser) {
		return friendRequestRepo.findByRequestToId(currentUser.getId());
	}

	public Iterable<FriendRequest> findRequestFrom(User currentUser) {
		return friendRequestRepo.findByRequestFromId(currentUser.getId());
	}
	
	public void addFriendRequest(User user, User currentUser) {
		FriendRequest friendReqest = new FriendRequest(DateUtil.getLocalDate(), currentUser, user);
		friendRequestRepo.save(friendReqest);		
	}
	
	public void deleteFriendRequest(FriendRequest frendReqest) {
		friendRequestRepo.delete(frendReqest);		
	}
	
	public void deleteFriendRequest(User user, User currentUser, FriendRequest frendReqest) {
		Long counterRequest = friendRequestRepo.findOneRequestId(user.getId(), currentUser.getId());
		if(counterRequest != null) {
			friendRequestRepo.deleteById(counterRequest);
		}
		friendRequestRepo.delete(frendReqest);	
	}

	public void addFriend(FriendRequest frendRequest) {
		User receivingUser = frendRequest.getRequestTo();
		User senderUser = frendRequest.getRequestFrom();
		receivingUser.getUserFriends().add(senderUser);
		userRepo.save(receivingUser);
		senderUser.getUserFriends().add(receivingUser);
		userRepo.save(senderUser);
	}

	public void deleteFriend(User user, User currentUser) {
		user.getUserFriends().remove(currentUser);
		userRepo.save(user);	
		currentUser.getUserFriends().remove(user);
		userRepo.save(currentUser);	
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