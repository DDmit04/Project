package com.web.repository;

import org.springframework.data.repository.CrudRepository;

import com.web.data.UserGroup;

public interface UserGroupRepo extends CrudRepository<UserGroup, Long> {
	
}
