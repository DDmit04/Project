package com.web.api;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.web.data.Group;
import com.web.data.User;
import com.web.data.dto.GroupDto;
import com.web.exceptions.GroupException;

public interface GroupService {
	
	Group createGroup(Group group, MultipartFile file, User currentUser) throws IllegalStateException, IOException, GroupException;
	
	void makeOwner(User currentUser, User user, Group group, String username, String password);
	
	void banUser(User currentUser, User user, Group group);

	void unbanUser(User currentUser, User user, Group group);
	
	void addGroupSub( User user, Group group);
	
	void removeGroupSub(User user, Group group);
	
	void addGroupAdmin(User currentUser, User user, Group group);

	void removeGroupAdmin(User currentUser, User user, Group group);
	
	

	
	GroupDto getOneGroup(User user, Group group);

	Iterable<GroupDto> getUserGroups(User user);
	
	Iterable<GroupDto> getAllGroups();

}
