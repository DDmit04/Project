package com.web.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.web.data.User;
import com.web.data.UserGroup;
import com.web.data.dto.PostDto;
import com.web.data.dto.UserDto;
import com.web.data.dto.UserGroupDto;
import com.web.repository.UserGroupRepo;
import com.web.service.GroupService;
import com.web.service.PostService;
import com.web.service.UserService;

@Controller
public class GroupController {
	
	@Autowired
	private GroupService groupService;
		
	@Autowired
	private UserService userServive;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private UserGroupRepo userGroupRepo;
	
	@GetMapping("/groups")
	public String getAllGroups(@AuthenticationPrincipal User currentUser,
							   Model model) {
		Iterable<UserGroupDto> allGroups = userGroupRepo.findAllDto();
		model.addAttribute("groups", allGroups);
		return "allGroupList";
	}
	
	@GetMapping("/groups/{group}")
	public String getGroup(@AuthenticationPrincipal User currentUser,
						   @PathVariable UserGroup group,
						   Model model) {
		UserGroupDto oneGroup = groupService.findOneGroup(group, currentUser);
		UserDto oneUser = userServive.findOneUserToGroup(currentUser, group);
		Iterable<PostDto> groupPosts = postService.findGroupPosts(currentUser, group);
		model.addAttribute("posts", groupPosts);
		model.addAttribute("group", oneGroup);
		model.addAttribute("user", oneUser);
		return "group";
	}
	
	@PostMapping("/groups/{group}")
	public String addGroupPost(@AuthenticationPrincipal User currentUser,
							   @PathVariable UserGroup group,
							   @RequestParam String postText,
							   @RequestParam String tags,
							   @RequestParam("file") MultipartFile file,
							   Model model) throws IllegalStateException, IOException {
		postService.addPost(postText, tags, file, group);
		return "redirect:/groups/" + group.getId();
	}
	
	@GetMapping("{user}/groups/create")
	public String createGroup() {
		return "createGroupForm";
	}
	
	@PostMapping("{user}/groups/create")
	public String createGroup(@AuthenticationPrincipal User currentUser,
							  @RequestParam String groupName,
							  @RequestParam (required = false)String groupTitle,
							  @RequestParam (required = false)String groupInformation,
							  @RequestParam("file") MultipartFile file,
							  Model model) throws IllegalStateException, IOException {
		UserGroup group = groupService.createGroup(groupName, groupInformation, groupTitle, file, currentUser);
		return "redirect:/groups/" + group.getId();
	}
	
	@GetMapping("/groups/{group}/sub")
	public String subGroup(@AuthenticationPrincipal User currentUser,
						   @PathVariable UserGroup group) {
		groupService.addGroupSub(group, currentUser);
		return "redirect:/groups/" + group.getId();
	}
	
	@GetMapping("/groups/{group}/unsub")
	public String unsubGroup(@AuthenticationPrincipal User currentUser,
							 @PathVariable UserGroup group) {
		groupService.removeGroupSub(group, currentUser);
		return "redirect:/groups/" + group.getId();
	}
	
	@GetMapping("/groups/{group}/socialList/{listType}")
	public String groupConnections(@AuthenticationPrincipal User currentUser,
								   @PathVariable String listType,
							 	   @PathVariable UserGroup group,
							 	   Model model) {
		UserGroupDto currentGroup = groupService.findOneGroup(group, currentUser);
		model.addAttribute("group", currentGroup);
		model.addAttribute("groupSubscrabers", group.getGroupSubs());
		model.addAttribute("groupAdmins", group.getGroupAdmins());
		model.addAttribute("listType", listType);
		return "groupConnections";
	}
	
}