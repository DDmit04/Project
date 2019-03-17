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
		   "   sum(case when ugs = :currentUser then 1 else 0 end) > 0" +
		   "   )" +
		   "   from UserGroup ug left join ug.groupSubs ugs" +
		   "   where ug.id = :groupId" +
		   "   group by ug")
	UserGroupDto findOneGroup(@Param("currentUser") User currentUser, @Param("groupId") Long groupId);
	
}
