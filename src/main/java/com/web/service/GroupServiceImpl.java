package com.web.service;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.web.api.FileService;
import com.web.api.GroupService;
import com.web.data.Group;
import com.web.data.User;
import com.web.data.dto.GroupDto;
import com.web.exceptions.GroupException;
import com.web.repository.CommentRepo;
import com.web.repository.GroupRepo;

@Service
public class GroupServiceImpl implements GroupService {
	
	private GroupRepo groupRepo;
	private FileService fileService;
	private CommentRepo commentRepo;
	private PasswordEncoder passwordEncoder; 
	
	@Autowired	
	public GroupServiceImpl(GroupRepo groupRepo, CommentRepo commentRepo, PasswordEncoder passwordEncoder, FileServiceImpl fileService) {
		this.groupRepo = groupRepo;
		this.fileService = fileService;
		this.commentRepo = commentRepo;
		this.passwordEncoder = passwordEncoder;
	}

	private boolean userIsGroupOwner(User currentUser, Group group) {
		return group.getGroupOwner().equals(currentUser);
	}
	
	private boolean userIsGroupAdmin(User currentUser, Group group) {
		return group.getGroupAdmins().contains(currentUser);
	}

	@Override
	public Group createGroup(Group group, MultipartFile file, User currentUser) 
			throws IllegalStateException, IOException, GroupException {
		
		Group groupFromDb = groupRepo.findByGroupName(group.getGroupName());
		if(groupFromDb != null) {
			throw new GroupException("group with name" + groupFromDb.getGroupName() + "already exists!", groupFromDb);
		}
		group.setGroupCreationDate(LocalDateTime.now(Clock.systemUTC()));
		group.setGroupPicName(fileService.uploadFile(file,UploadType.GROUP_PIC));
		group.setGroupOwner(currentUser);
		groupRepo.save(group);	
		group.getGroupSubs().add(currentUser);
		group.getGroupAdmins().add(currentUser);
		groupRepo.save(group);	
		return group;
	}
	
	@Override
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
	
	@Override
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

	@Override
	public void unbanUser(User currentUser, User user, Group group) {
		if(userIsGroupOwner(currentUser, group) || userIsGroupAdmin(currentUser, group)) {
			group.getGroupBanList().remove(user);
			groupRepo.save(group);		
		}
	}
	
	@Override
	public void addGroupSub(User user, Group group) {
		group.getGroupSubs().add(user);
		groupRepo.save(group);
	}

	@Override
	public void removeGroupSub(User user, Group group) {
		group.getGroupSubs().remove(user);
		groupRepo.save(group);		
	}

	@Override
	public void addGroupAdmin(User currentUser, User user, Group group) {
		if(userIsGroupOwner(currentUser, group)) {
			group.getGroupAdmins().add(user);
			groupRepo.save(group);		
		}
	}
	
	@Override
	public void removeGroupAdmin(User currentUser, User user, Group group) {
		if(userIsGroupOwner(currentUser, group) || user.equals(currentUser)) {
			group.getGroupAdmins().remove(user);
			groupRepo.save(group);	
		}
	}
	
	@Override
	public GroupDto getOneGroup(User user, Group group) {
		return groupRepo.findOneGroupDto(group.getId());
	}

	@Override
	public Iterable<GroupDto> getUserGroups(User user) {
		return groupRepo.findUserGroupsDto(user);
	}
	
	@Override
	public Iterable<GroupDto> getAllGroups() {
		return groupRepo.findAllGroupsDto();
	}

}