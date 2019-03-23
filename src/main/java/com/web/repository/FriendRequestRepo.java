package com.web.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.web.data.FriendRequest;

public interface FriendRequestRepo extends CrudRepository<FriendRequest, Long> {
	
	Iterable<FriendRequest> findByRequestFromId(Long requestFromId);
	
	Iterable<FriendRequest> findByRequestToId(Long requestToId);

	@Query("select id " + 
		   "from FriendRequest fr " + 
		   "where fr.requestToId = :requestToId and fr.requestFromId = :requestFromId " +
		   "group by fr"
		)
	Long findOneRequestId(@Param("requestToId") Long requestToId, @Param("requestFromId") Long requestFromId);
	
}
