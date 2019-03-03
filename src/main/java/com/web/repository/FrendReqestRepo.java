package com.web.repository;

import org.springframework.data.repository.CrudRepository;

import com.web.data.FriendRequest;
import com.web.data.User;

public interface FrendReqestRepo extends CrudRepository<FriendRequest, Long> {
	
	Iterable<FriendRequest> findByRequestFrom(User user);
	
	Iterable<FriendRequest> findByRequestTo(User user);
	
}
