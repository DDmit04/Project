package com.web.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.web.data.Chat;
import com.web.data.User;
import com.web.data.dto.ChatDto;

public interface ChatRepo extends CrudRepository<Chat, Long>{
	
	@Query("from Chat ch where ch.id = :id group by ch")
	Chat findChatById(Long id);
	
	@Query("select new com.web.data.dto.ChatDto(" +
		    "   ch " +
		    ") " +
		    "from Chat ch left join ch.chatsArcive ca " +
		    "             left join ch.chatMembers cm " +
		    " where cm = :currentUser or ca = :currentUser " +
		    " group by ch")
	Iterable<ChatDto> findUserChats(@Param("currentUser") User currentUser);
	
	@Query("select new com.web.data.dto.ChatDto(" +
			"  ch,  " +
			"  (select count(*) from ch.chatMembers), " +
			"  (select count(*) from ch.chatAdmins), " +
			"  (select count(*) from ch.chatBanList) " +
			") " +
			" from Chat ch " +
			" where ch.id = :chatId" +
			" group by ch")
	ChatDto findOneChat(@Param("chatId") Long chatId);

}
