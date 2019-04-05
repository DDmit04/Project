package com.web.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.web.data.Group;
import com.web.data.User;
import com.web.data.dto.GroupDto;
import com.web.exceptions.GroupException;
import com.web.repository.CommentRepo;
import com.web.repository.GroupRepo;
import com.web.utils.DateUtil;

@Service
public class GroupService {
	
	@Autowired
	private GroupRepo groupRepo;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private CommentRepo commentRepo;

	public Group createGroup(String groupName, String groupInformation, String groupTitle, MultipartFile file, User currentUser) 
			throws IllegalStateException, IOException, GroupException {
		
		Group groupFromDb = groupRepo.findByGroupName(groupName);
		if(groupFromDb != null) {
			throw new GroupException("group with name" + groupFromDb.getGroupName() + "already exists!", groupFromDb);
		}
		Group group = new Group (groupName, groupInformation, groupTitle, DateUtil.getLocalDate());
		group.setGroupPicName(fileService.uploadFile(file,UploadType.GROUP_PIC));
		group.setGroupOwner(currentUser);
		groupRepo.save(group);	
		group.getGroupSubs().add(currentUser);
		group.getGroupAdmins().add(currentUser);
		groupRepo.save(group);	
		return group;
	}
	
	public void makeOwner(Group group, User user) {
		group.setGroupOwner(user);
		groupRepo.save(group);
	}
	
	public void banUser(Group group, User user) {
		group.getBanList().add(user);
		groupRepo.save(group);
		commentRepo.deleteAll(commentRepo.findBannedComments(group, user));
	}

	public void unbanUser(Group group, User user) {
		group.getBanList().remove(user);
		groupRepo.save(group);		
	}
	
	public void addGroupSub(Group group, User user) {
		group.getGroupSubs().add(user);
		groupRepo.save(group);
	}

	public void removeGroupSub(Group group, User user) {
		group.getGroupSubs().remove(user);
		groupRepo.save(group);		
	}

	public void addGroupAdmin(Group group, User user) {
		group.getGroupAdmins().add(user);
		groupRepo.save(group);		
	}

	public void removeGroupAdmin(Group group, User user) {
		group.getGroupAdmins().remove(user);
		groupRepo.save(group);				
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