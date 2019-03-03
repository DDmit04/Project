package com.web.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.data.FriendRequest;
import com.web.data.User;
import com.web.repository.FrendReqestRepo;
import com.web.repository.UserRepo;

@Service
public class ProfileService {
	
	@Autowired
	private FrendReqestRepo frendReqestRepo;
	
	@Autowired
	private UserRepo userRepo;

	public void addFriendRequest(String frendReqestText, User user, User currentUser) {
		FriendRequest frendReqest = new FriendRequest(" ", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), currentUser, user);
		frendReqestRepo.save(frendReqest);		
	}

	public Iterable<FriendRequest> findRequestTo(User currentUser) {
		return frendReqestRepo.findByRequestTo(currentUser);
	}

	public Iterable<FriendRequest> findRequestFrom(User currentUser) {
		return frendReqestRepo.findByRequestFrom(currentUser);
	}
	
	public void deleteRequest(FriendRequest frendReqest) {
		frendReqestRepo.delete(frendReqest);		
	}

	public void addFriend(User user, User currentUser, FriendRequest frendReqest) {
		currentUser.getUserFriends().add(user);
		userRepo.save(user);
		user.getUserFriends().add(currentUser);
		userRepo.save(currentUser);
		frendReqestRepo.delete(frendReqest);		
	}

	public void deleteFriend(User user, User currentUser) {
		currentUser.getUserFriends().remove(user);
		userRepo.save(user);
		user.getUserFriends().remove(currentUser);
		userRepo.save(currentUser);		
	}
}