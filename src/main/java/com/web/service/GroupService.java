package com.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.data.User;
import com.web.data.UserGroup;
import com.web.repository.UserGroupRepo;
import com.web.repository.UserRepo;
import com.web.utils.DateUtil;

@Service
public class GroupService {
	
	@Autowired
	private UserGroupRepo groupRepo;
	
	@Autowired
	private UserRepo userRepo;


	public void createGroup(String groupName, String groupInformation, String groupTitle, User currentUser) {
		UserGroup group = new UserGroup (groupName, groupInformation, groupTitle, DateUtil.getLocalDate());
		group.setGroupOwner(currentUser);
		groupRepo.save(group);	
		group.getGroupAdmins().add(currentUser);
		groupRepo.save(group);
	}
	
	public void addGroupSub(UserGroup group, User user) {
		group.getGroupSubs().add(user);
		groupRepo.save(group);
	}

	public void removeGroupSub(UserGroup group, User user) {
		group.getGroupSubs().remove(user);
		groupRepo.save(group);		
	}

	public void addGroupAdmin(UserGroup group, User user) {
		group.getGroupAdmins().add(user);
		groupRepo.save(group);		
	}

	public void removeGroupAdmin(UserGroup group, User user) {
		group.getGroupAdmins().remove(user);
		groupRepo.save(group);				
	}

}
