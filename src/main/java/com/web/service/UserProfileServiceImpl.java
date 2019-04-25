package com.web.service;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.web.api.user.UserProfileService;
import com.web.data.FriendRequest;
import com.web.data.User;
import com.web.data.dto.FriendRequestDto;
import com.web.repository.CommentRepo;
import com.web.repository.FriendRequestRepo;
import com.web.repository.UserRepo;

@Service
public class UserProfileServiceImpl implements UserProfileService {
	
	@Autowired
	private FriendRequestRepo friendRequestRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private FileServiceImpl fileService;
	
	@Override
	public void createFriendRequest(User user, User currentUser) {
		FriendRequest friendReqest = new FriendRequest(LocalDateTime.now(Clock.systemUTC()), currentUser, user);
		friendRequestRepo.save(friendReqest);		
	}
	
	@Override
	public void deleteFriendRequest(FriendRequest frendReqest) {
		friendRequestRepo.delete(frendReqest);		
	}
	
	@Override
	public void deleteFriendRequest(User user, User currentUser, FriendRequest frendReqest) {
		FriendRequest counterRequest = friendRequestRepo.findOneRequest(user, currentUser);
		if(counterRequest != null) {
			friendRequestRepo.delete(counterRequest);
		}
		deleteFriendRequest(frendReqest);	
	}
	
	@Override
	public void addFriend(FriendRequest frendRequest) {
		User receivingUser = frendRequest.getRequestTo();
		User senderUser = frendRequest.getRequestFrom();
		receivingUser.getUserFriends().add(senderUser);
		userRepo.save(receivingUser);
		senderUser.getUserFriends().add(receivingUser);
		userRepo.save(senderUser);
	}

	@Override
	public void deleteFriend(User user, User currentUser) {
		user.getUserFriends().remove(currentUser);
		userRepo.save(user);	
		currentUser.getUserFriends().remove(user);
		userRepo.save(currentUser);	
	}
	
	@Override
	public void removeSubscription(User user, User currentUser) {
		user.getSubscribers().remove(currentUser);
		userRepo.save(user);
	}

	@Override
	public void addSubscription(User user, User currentUser) {
		user.getSubscribers().add(currentUser);
		userRepo.save(user);
	}

	@Override
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
		FriendRequest counterRequestTo = friendRequestRepo.findOneRequest(user, currentUser);
		FriendRequest counterRequestFrom = friendRequestRepo.findOneRequest(currentUser, user);
		if(counterRequestFrom != null) {
			friendRequestRepo.delete(counterRequestFrom);
		}
		if(counterRequestTo != null) {
			friendRequestRepo.delete(counterRequestTo);
		}
		commentRepo.deleteAll(commentRepo.findBannedComments(currentUser, user));	
	}
	
	@Override
	public void updateUserProfile(User user, MultipartFile file, String userInformation, String userTitle) throws IllegalStateException, IOException {
		if(userInformation != null) {
			//
		}
		if(userTitle != null) {
			//
		}
		uploadUserPic(user, file);
		userRepo.save(user);		
	}
	
	@Override
	public void uploadUserPic(User user, MultipartFile userPic) throws IllegalStateException, IOException {
		user.setUserPicName(fileService.uploadFile(userPic, UploadType.USER_PIC));
	}
		
	@Override
	public void removeFromBlackList(User user, User currentUser) {
		currentUser.getBlackList().remove(user);
		userRepo.save(currentUser);
	}
	
	@Override
	public Iterable<FriendRequestDto> getFriendRequestsToUser(User currentUser) {
		return friendRequestRepo.findByRequestToId(currentUser);
	}

	@Override
	public Iterable<FriendRequestDto> getFriendRequestsFromUser(User currentUser) {
		return friendRequestRepo.findByRequestFromId(currentUser);
	}

}