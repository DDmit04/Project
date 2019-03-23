package com.web.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.web.data.User;
import com.web.data.UserGroup;
import com.web.data.dto.UserGroupDto;
import com.web.repository.UserGroupRepo;
import com.web.repository.UserRepo;
import com.web.utils.DateUtil;

@Service
public class GroupService {
	
	@Autowired
	private UserGroupRepo groupRepo;
	
	@Autowired
	private FileService fileService;


	public UserGroup createGroup(String groupName, String groupInformation, String groupTitle, MultipartFile file, User currentUser) throws IllegalStateException, IOException {
		UserGroup group = new UserGroup (groupName, groupInformation, groupTitle, DateUtil.getLocalDate());
		group.setGroupPicName(fileService.uploadFile(file,UploadType.GROUP_PIC));
		group.setGroupOwner(currentUser);
		groupRepo.save(group);	
		addGroupAdmin(group, currentUser);
		return group;
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

	public UserGroupDto findOneGroup(UserGroup group, User currentUser) {
		return groupRepo.findOneGroup(group.getId(), currentUser);
	}

}
