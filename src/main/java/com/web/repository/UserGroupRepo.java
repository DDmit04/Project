package com.web.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.web.data.User;
import com.web.data.UserGroup;
import com.web.data.dto.UserGroupDto;

public interface UserGroupRepo extends CrudRepository<UserGroup, Long> {
	
	@Query("   select new com.web.data.dto.UserGroupDto(" +
		   "   ug, " +
		   "   (select count(*) from ug.groupSubs), " +
		   "   (select count(*) from ug.groupAdmins), " +
		   "   sum(case when ga = :currentUser then 1 else 0 end) > 0" +
		   "   )" +
		   "   from UserGroup ug left join ug.groupAdmins ga" +
		   "   where ug.id = :groupId" +
		   "   group by ug")
	UserGroupDto findOneGroup(@Param("groupId") Long groupId, @Param("currentUser") User currentUser);
	
	@Query("   select new com.web.data.dto.UserGroupDto(" +
			   "   ug, " +
			   "   (select count(*) from ug.groupSubs) " +
			   "   )" +
			   "   from UserGroup ug left join ug.groupSubs us" +
			   "   where us = :currentUser " + 
			   "   group by ug")
	Iterable<UserGroupDto> findAllDto(@Param("currentUser") User currentUser);
	
	@Query("   select new com.web.data.dto.UserGroupDto(" +
			   "   ug, " +
			   "   (select count(*) from ug.groupSubs) " +
			   "   )" +
			   "   from UserGroup ug " +
			   "   group by ug")
	Iterable<UserGroupDto> findAllDto();
	
}
