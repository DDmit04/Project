package com.web.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.web.data.Chat;
import com.web.data.ChatSession;
import com.web.data.User;

public interface ChatSessionRepo extends CrudRepository<ChatSession, Long> {
	
	@Query("from ChatSession ch where ch.connectedChat = :chat and ch.connectedUser = :user group by ch")
	ChatSession findSessionByChatAndUser(@Param("chat") Chat chat, @Param("user") User user);

	@Query("from ChatSession ch where ch.connectedUser = :user group by ch")
	Iterable<ChatSession> findSessionsByUser(@Param("user") User user);

}
