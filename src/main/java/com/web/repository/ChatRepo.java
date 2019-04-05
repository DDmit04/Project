package com.web.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.web.data.Chat;

public interface ChatRepo extends CrudRepository<Chat, Long>{
	
	@Query("from Chat ch where ch.id = :id group by ch")
	Chat findById1(Long id);

}
