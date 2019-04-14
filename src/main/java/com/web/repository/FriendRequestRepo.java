package com.web.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.web.data.FriendRequest;
import com.web.data.User;
import com.web.data.dto.FriendRequestDto;

public interface FriendRequestRepo extends CrudRepository<FriendRequest, Long> {
	
	
	@Query("select new com.web.data.dto.FriendRequestDto(" +
			"   fr " +
			") " +
			" from FriendRequest fr " +
			" where fr.requestFrom = :userRequestFrom " +
			" group by fr")
	Iterable<FriendRequestDto> findByRequestFromId(@Param("userRequestFrom") User user);
	
	@Query("select new com.web.data.dto.FriendRequestDto(" +
			"   fr" +
			") " +
			" from FriendRequest fr " +
			" where fr.requestTo = :userRequestTo " +
			" group by fr")
	Iterable<FriendRequestDto> findByRequestToId(@Param("userRequestTo") User user);

	@Query("from FriendRequest fr " + 
		   "  where fr.requestTo = :requestTo and fr.requestFrom = :requestFrom " +
		   "  group by fr"
		)
	FriendRequest findOneRequest(@Param("requestTo") User userTo, @Param("requestFrom") User userFrom);
	
}
