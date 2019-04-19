package com.web.service;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.web.data.Group;
import com.web.data.User;
import com.web.data.dto.GroupDto;
import com.web.exceptions.GroupException;
import com.web.repository.CommentRepo;
import com.web.repository.GroupRepo;

@Service
public class GroupService {
	
	@Autowired
	private GroupRepo groupRepo;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder; 

	public Group createGroup(String groupName, String groupInformation, String groupTitle, MultipartFile file, User currentUser) 
			throws IllegalStateException, IOException, GroupException {
		
		Group groupFromDb = groupRepo.findByGroupName(groupName);
		if(groupFromDb != null) {
			throw new GroupException("group with name" + groupFromDb.getGroupName() + "already exists!", groupFromDb);
		}
		Group group = new Group (groupName, groupInformation, groupTitle, LocalDateTime.now(Clock.systemUTC()));
		group.setGroupPicName(fileService.uploadFile(file,UploadType.GROUP_PIC));
		group.setGroupOwner(currentUser);
		groupRepo.save(group);	
		group.getGroupSubs().add(currentUser);
		group.getGroupAdmins().add(currentUser);
		groupRepo.save(group);	
		return group;
	}
	
	public void makeOwner(User currentUser, User user, Group group, String username, String password) {
		// Password encoder!!!
		User groupOwner = group.getGroupOwner();
		if (groupOwner.equals(currentUser) 
				&& username.equals(groupOwner.getUsername())
				&& passwordEncoder.matches(password, groupOwner.getPassword())) {
			group.setGroupOwner(user);
			groupRepo.save(group);
		}
	}
	
	public void banUser(User currentUser, User user, Group group) {
		if(userIsGroupOwner(currentUser, group) || userIsGroupAdmin(currentUser, group)) {
			group.getGroupBanList().add(user);
			if(group.getGroupAdmins().contains(user)) {
				group.getGroupAdmins().remove(user);
			}
			groupRepo.save(group);
			commentRepo.deleteAll(commentRepo.findBannedComments(group, user));
		}
	}

	public void unbanUser(User currentUser, User user, Group group) {
		if(userIsGroupOwner(currentUser, group) || userIsGroupAdmin(currentUser, group)) {
			group.getGroupBanList().remove(user);
			groupRepo.save(group);		
		}
	}
	
	public void addGroupSub(Group group, User user) {
		group.getGroupSubs().add(user);
		groupRepo.save(group);
	}

	public void removeGroupSub(User user, Group group) {
		group.getGroupSubs().remove(user);
		groupRepo.save(group);		
	}

	public void addGroupAdmin(User currentUser, User user, Group group) {
		if(userIsGroupOwner(currentUser, group)) {
			group.getGroupAdmins().add(user);
			groupRepo.save(group);		
		}
	}

	public void removeGroupAdmin(User currentUser, User user, Group group) {
		if(userIsGroupOwner(currentUser, group) || user.equals(currentUser)) {
			group.getGroupAdmins().remove(user);
			groupRepo.save(group);	
		}
	}
	
	public boolean userIsGroupOwner(User currentUser, Group group) {
		return group.getGroupOwner().equals(currentUser);
	}
	
	public boolean userIsGroupAdmin(User currentUser, Group group) {
		return group.getGroupAdmins().contains(currentUser);
	}

	public GroupDto findOneGroup(Group group, User currentUser) {
		return groupRepo.findOneGroupDto(group.getId());
	}

	public Iterable<GroupDto> findUserGroupsDto(User user) {
		return groupRepo.findUserGroupsDto(user);
	}
	
	public Iterable<GroupDto> findAllGroupsDto() {
		return groupRepo.findAllGroupsDto();
	}
}