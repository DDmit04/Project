package com.web.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.web.data.ChatSessionConnection;

public interface ChatSessionConnectionRepo extends CrudRepository<ChatSessionConnection, Long> {
	
	@Query("from ChatSessionConnection csc where csc.id = :id group by csc")
	ChatSessionConnection findConnectionById(Long id);

}
