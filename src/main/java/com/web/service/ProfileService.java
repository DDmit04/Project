package com.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.data.Comment;
import com.web.data.FriendRequest;
import com.web.data.Post;
import com.web.data.User;
import com.web.repository.CommentRepo;
import com.web.repository.FriendRequestRepo;
import com.web.repository.UserRepo;
import com.web.utils.DateUtil;
import com.web.utils.ServiceUtils;

@Service
public class ProfileService {
	
	@Autowired
	private FriendRequestRepo friendRequestRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	public void addFriendRequest(User user, User currentUser) {
		FriendRequest friendReqest = new FriendRequest(DateUtil.getLocalDate(), currentUser, user);
		friendRequestRepo.save(friendReqest);		
	}
	
	public void deleteFriendRequest(FriendRequest frendReqest) {
		friendRequestRepo.delete(frendReqest);		
	}
	
	public void deleteFriendRequest(User user, User currentUser, FriendRequest frendReqest) {
		Long counterRequest = friendRequestRepo.findOneRequestById(user.getId(), currentUser.getId());
		if(counterRequest != null) {
			friendRequestRepo.deleteById(counterRequest);
		}
		deleteFriendRequest(frendReqest);	
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
	
	public void removeSubscription(User user, User currentUser) {
		user.getSubscribers().remove(currentUser);
		userRepo.save(user);
	}

	public void addSubscription(User user, User currentUser) {
		user.getSubscribers().add(currentUser);
		userRepo.save(user);
	}

	public void addInBlackList(User user, User currentUser) {
		currentUser.getBlackList().add(user);		
		if(currentUser.getSubscribers().contains(user)) {
			removeSubscription(user, currentUser);
			removeSubscription(currentUser, user);
		}
		if(currentUser.getUserFriends().contains(user)) {
			deleteFriend(user, currentUser);
		}
		userRepo.save(currentUser);
		Long counterRequest = friendRequestRepo.findOneRequestById(user.getId(), currentUser.getId());
		Long counterRequest1 = friendRequestRepo.findOneRequestById(currentUser.getId(), user.getId());
		if(counterRequest1 != null) {
			friendRequestRepo.deleteById(counterRequest1);
		}
		if(counterRequest != null) {
			friendRequestRepo.deleteById(counterRequest);
		}
		commentRepo.deleteAll(ServiceUtils.findBannedComments(currentUser, user));	
	}
	
	public void removeFromBlackList(User user, User currentUser) {
		currentUser.getBlackList().remove(user);
		userRepo.save(currentUser);
	}
	
	public Iterable<FriendRequest> findRequestTo(User currentUser) {
		return friendRequestRepo.findByRequestToId(currentUser.getId());
	}

	public Iterable<FriendRequest> findRequestFrom(User currentUser) {
		return friendRequestRepo.findByRequestFromId(currentUser.getId());
	}
}