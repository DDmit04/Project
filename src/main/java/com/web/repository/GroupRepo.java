package com.web.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.web.data.User;
import com.web.data.Group;
import com.web.data.dto.GroupDto;

public interface GroupRepo extends CrudRepository<Group, Long> {
	
	Group findByGroupName(String groupName);
	
	@Query("select new com.web.data.dto.GroupDto(" +
		   "   ug, " +
		   "   (select count(*) from ug.groupSubs), " +
		   "   (select count(*) from ug.groupAdmins), " +
		   "   (select count(*) from ug.banList) " + 
		   "   )" +
		   "   from Group ug " +
		   "   where ug.id = :groupId" +
		   "   group by ug")
	GroupDto findOneGroupDto(@Param("groupId") Long groupId);
	
	@Query("select new com.web.data.dto.GroupDto(" +
		   "   ug, " +
		   "   (select count(*) from ug.groupSubs) " +
		   "   )" +
		   "   from Group ug left join ug.groupSubs us" +
		   "   where us = :currentUser " + 
		   "   group by ug")
	Iterable<GroupDto> findUserGroupsDto(@Param("currentUser") User currentUser);
	
	@Query("select new com.web.data.dto.GroupDto(" +
		   "   ug, " +
		   "   (select count(*) from ug.groupSubs) " +
		   "   )" +
		   "   from Group ug " +
		   "   group by ug")
	Iterable<GroupDto> findAllGroupsDto();
	
}
