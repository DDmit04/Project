package com.web.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.web.data.User;
import com.web.data.Group;
import com.web.data.dto.GroupDto;
import com.web.repository.GroupRepo;
import com.web.utils.DateUtil;

@Service
public class GroupService {
	
	@Autowired
	private GroupRepo groupRepo;
	
	@Autowired
	private FileService fileService;


	public Group createGroup(String groupName, String groupInformation, String groupTitle, MultipartFile file, User currentUser) throws IllegalStateException, IOException {
		Group group = new Group (groupName, groupInformation, groupTitle, DateUtil.getLocalDate());
		group.setGroupPicName(fileService.uploadFile(file,UploadType.GROUP_PIC));
		group.setGroupOwner(currentUser);
		groupRepo.save(group);	
		addGroupAdmin(group, currentUser);
		return group;
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
		return groupRepo.findOneGroup(group.getId(), currentUser);
	}

	public Iterable<GroupDto> findAllGroupsDto(User user) {
		return groupRepo.findAllGroupsDto(user);
	}
}