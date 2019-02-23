package com.web.repository;

import org.springframework.data.repository.CrudRepository;

import com.web.data.User;

public interface UserRepo extends CrudRepository<User, Long> {
	
	User findByUsername(String username);

}
