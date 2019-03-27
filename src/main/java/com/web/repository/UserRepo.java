package com.web.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.web.data.User;
import com.web.data.Group;
import com.web.data.dto.UserDto;

public interface UserRepo extends CrudRepository<User, Long> {
	
	User findByUsername(String username);
	
	@Query("select new com.web.data.dto.UserDto(" +
            "   u, " +
			"   (select count(*) from u.userFriends), " +
            "   (select count(*) from u.subscribers), " +
            "   (select count(*) from u.subscriptions), " +
			"   (select count(*) from u.subedGroups), " + 
            "   sum(case when uf = :currentUser then 1 else 0 end) > 0, " +
			"   sum(select count(requestFrom) from FriendRequest where requestFromId = :currentUserId) > 0, " +
            "   sum(case when us = :currentUser then 1 else 0 end) > 0, " +
            "   sum(case when bloking = :currentUser then 1 else 0 end) > 0, " +
            "   sum(case when bloked = :currentUser then 1 else 0 end) > 0 " +
            ") " +
            " from User u left join u.userFriends uf " +
            " 			  left join u.subscribers us " +
            "             left join u.inBlackList bloking " +
            "             left join u.blackList bloked " +
            " where u.id = :id " +
            " group by u")
    UserDto findOneUserToUser(@Param("currentUser") User currentUser, @Param("currentUserId") Long currentUserId, @Param("id") Long id);
	
	@Query("select new com.web.data.dto.UserDto(" +
            "   u, " +
			"   sum(case when sg = :group then 1 else 0 end) > 0, " +
            "   sum(case when ag = :group then 1 else 0 end) > 0, " +
            "   sum(case when bg = :group then 1 else 0 end) > 0 " +
            ") " +
            " from User u left join u.subedGroups sg " +
            "			  left join u.adminedGroups ag " +
            "             left join u.bannedInGroups bg " +
            " where u.id = :currentUserId " +
            " group by u")
    UserDto findOneUserToGroup(@Param("currentUserId") Long currentUserId, @Param("group") Group group);	
	
	@Query("select new com.web.data.dto.UserDto(" +
            "   u, " +
			"   (select count(*) from u.userFriends), " +
            "   (select count(*) from u.subscriptions), " +
			"   (select count(*) from u.subscribers), " + 
            "   (select count(*) from u.subedGroups), " + 
			"   (select count(*) from u.blackList) " + 
            ") " +
            " from User u " +
            " where u.id = :id " +
            " group by u")
    UserDto findOneUserForList(@Param("id") Long id);		
	
}
