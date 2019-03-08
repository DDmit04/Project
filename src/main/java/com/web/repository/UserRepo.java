package com.web.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.web.data.User;
import com.web.data.dto.UserDto;

public interface UserRepo extends CrudRepository<User, Long> {
	
	User findByUsername(String username);
	
	@Query("select new com.web.data.dto.UserDto(" +
            "   u, " +
			"   count(uf), " +
            "   (select count(*) from u.subscribers), " +
            "   sum(case when uf = :currentUser then 1 else 0 end) > 0, " +
			"   sum(select count(requestFrom) from FriendRequest where requestFromId = :currentUserId) > 0, " +
            "   sum(case when us = :currentUser then 1 else 0 end) > 0" +
            ") " +
            "from User u left join u.userFriends uf left join u.subscribers us " +
            "where u.id = :id " +
            "group by u")
    UserDto findOneUser(@Param("currentUser") User currentUser, @Param("currentUserId") Long currentUserId, @Param("id") Long id);	
	
	@Query("select new com.web.data.dto.UserDto(" +
            "   u, " +
			"   count(uf), " +
            "   count(us) " +
            ") " +
            "from User u left join u.userFriends uf left join u.subscribers us " +
            "where u.id = :id " +
            "group by u")
    UserDto findOneUser(@Param("id") Long id);	
}
