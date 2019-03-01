package com.web.repository;

import org.springframework.data.repository.CrudRepository;

import com.web.data.Group;

public interface GroupRepo extends CrudRepository<Group, Long> {
	
}
