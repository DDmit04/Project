package com.web.repository;

import org.springframework.data.repository.CrudRepository;

import com.web.data.FrendReqest;
import com.web.data.User;

public interface FrendReqestRepo extends CrudRepository<FrendReqest, Long> {
	
	Iterable<FrendReqest> findByReqiestFrom(User user);
	
	Iterable<FrendReqest> findByReqiestTo(User user);
	
}
