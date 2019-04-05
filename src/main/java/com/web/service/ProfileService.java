package com.web.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.web.data.FriendRequest;
import com.web.data.User;
import com.web.repository.CommentRepo;
import com.web.repository.FriendRequestRepo;
import com.web.repository.UserRepo;
import com.web.utils.DateUtil;

@Service
public class ProfileService {
	
	@Autowired
	private FriendRequestRepo friendRequestRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private FileService fileService;
	
	public void addFriendRequest(User user, User currentUser) {
		FriendRequest friendReqest = new FriendRequest(DateUtil.getLocalDate(), currentUser, user);
		friendRequestRepo.save(friendReqest);		
	}
	
	public void deleteFriendRequest(FriendRequest frendReqest) {
		friendRequestRepo.delete(frendReqest);		
	}
	
	public void deleteFriendRequest(User user, User currentUser, FriendRequest frendReqest) {
		FriendRequest counterRequest = friendRequestRepo.findOneRequest(user.getId(), currentUser.getId());
		if(counterRequest != null) {
			friendRequestRepo.delete(counterRequest);
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
		FriendRequest counterRequestTo = friendRequestRepo.findOneRequest(user.getId(), currentUser.getId());
		FriendRequest counterRequestFrom = friendRequestRepo.findOneRequest(currentUser.getId(), user.getId());
		if(counterRequestFrom != null) {
			friendRequestRepo.delete(counterRequestFrom);
		}
		if(counterRequestTo != null) {
			friendRequestRepo.delete(counterRequestTo);
		}
		commentRepo.deleteAll(commentRepo.findBannedComments(currentUser, user));	
	}
	
	public void updateUserProfile(User currentUser, MultipartFile file, String userInformation, String userTitle) throws IllegalStateException, IOException {
		if(userInformation != null) {
			//
		}
		if(userTitle != null) {
			//
		}
		currentUser.setUserPicName(fileService.uploadFile(file, UploadType.USER_PIC));
		userRepo.save(currentUser);		
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