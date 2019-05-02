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

import com.web.api.GroupService;
import com.web.api.post.PostService;
import com.web.api.user.UserService;
import com.web.data.Group;
import com.web.data.Post;
import com.web.data.User;
import com.web.data.dto.GroupDto;
import com.web.data.dto.PostDto;
import com.web.data.dto.UserDto;
import com.web.exceptions.GroupException;
import com.web.service.GroupServiceImpl;
import com.web.service.PostServiceImpl;
import com.web.service.UserServiceImpl;

@Controller
public class GroupController {
	
	private PostService postService;
	private GroupService groupService;
	private UserService userService;
	
	@Autowired
	public GroupController(UserServiceImpl userServiceImpl, GroupServiceImpl groupService, PostServiceImpl postService) {
		this.userService = userServiceImpl;
		this.groupService = groupService;
		this.postService = postService;
	}
	
	@GetMapping("/groups")
	public String getAllGroups(@AuthenticationPrincipal User currentUser,
							   Model model) {
		Iterable<GroupDto> allGroups = groupService.getAllGroups();
		Iterable<GroupDto> userGroups = groupService.getUserGroups(currentUser);
		model.addAttribute("allGroups", allGroups);
		model.addAttribute("userGroups", userGroups);
		model.addAttribute("userGroupsCount", userGroups.spliterator().getExactSizeIfKnown());
		return "groupList";
	}
	
	@GetMapping("/groups/{group}")
	public String getGroup(@AuthenticationPrincipal User currentUser,
						   @PathVariable Group group,
						   Model model) {
		GroupDto oneGroup = groupService.getOneGroup(currentUser, group);
		UserDto oneUser = userService.getOneUserToGroup(currentUser, group);
		Iterable<GroupDto> adminedGroups = groupService.getAdminedGroups(currentUser);
		Iterable<PostDto> groupPosts = postService.getGroupPosts(currentUser, group);
		model.addAttribute("posts", groupPosts);
		model.addAttribute("group", oneGroup);
		model.addAttribute("groupSubs", group.getGroupSubs());
		model.addAttribute("user", oneUser);
		model.addAttribute("adminedGroups", adminedGroups);
		return "group";
	}
	
	@PostMapping("/groups/{group}")
	public String addGroupPost(@AuthenticationPrincipal User currentUser,
							   @PathVariable Group group,
							   @RequestParam("file") MultipartFile file,
							   Post post,
							   Model model) throws IllegalStateException, IOException {
		postService.createPost(group, post, file);
		return "redirect:/groups/" + group.getId();
	}
	
	@GetMapping("{user}/groups/create")
	public String createGroup() {
		return "createGroup";
	}
	
	@PostMapping("{user}/groups/create")
	public String createGroup(@AuthenticationPrincipal User currentUser,
							  @RequestParam("file") MultipartFile file,
							  Model model,
							  Group group) throws IllegalStateException, IOException {
		try {
			group = groupService.createGroup(group, file, currentUser);
		} catch (GroupException e) {
			model.addAttribute("groupNameError", e.getMessage());
			model.addAttribute("registrationName", e.getGroup().getGroupName());
			return "createGroup";
		}
		return "redirect:/groups/" + group.getId();
	}
	
	@GetMapping("/groups/{group}/redact")
	public String redactProfile(@AuthenticationPrincipal User currentUser,
								@PathVariable Group group,
								Model model) {
		if(group.getGroupOwner().equals(currentUser) || group.getGroupAdmins().contains(currentUser)) {
			model.addAttribute("group", group);
			return "groupRedaction";
		} else {
			return "redirect:/groups/" + group.getId();
		}
	}
	
	@PostMapping("/groups/{group}/redact")
	public String redactProfile(@AuthenticationPrincipal User currentUser,
								@PathVariable Group group,
							  	@RequestParam(required = false) String groupTitle,
							  	@RequestParam(required = false) String groupInformation, 
							  	@RequestParam("file") MultipartFile file,
							  	Model model) throws IllegalStateException, IOException {
		groupService.updateGroupInformation(group, file, groupInformation, groupTitle);
		model.addAttribute("group", group);
		return "redirect:/groups/" + group.getId() + "/redact";
	}
	
	@GetMapping("/groups/{group}/{user}/sub")
	public String subGroup(@AuthenticationPrincipal User currentUser,
						   @PathVariable User user,
						   @PathVariable Group group) {
		if(user.equals(currentUser)) {
			groupService.addGroupSub(user, group); 
		}
		return "redirect:/groups/" + group.getId();
	}
	
	@GetMapping("/groups/{group}/{user}/unsub")
	public String unsubGroup(@AuthenticationPrincipal User currentUser,
							 @PathVariable User user,
							 @PathVariable Group group) {
		if(user.equals(currentUser)) {
			groupService.removeGroupSub(user, group);
		}
		return "redirect:/groups/" + group.getId();
	}
	
	@GetMapping("/groups/{group}/{user}/addAdmin")
	public String addAdmin(@AuthenticationPrincipal User currentUser,
						   @PathVariable Group group,
						   @PathVariable User user) {
		groupService.addGroupAdmin(currentUser, user, group);
		return "redirect:/groups/" + group.getId() + "/socialList/groupAdmins";
	}
	
	@GetMapping("/groups/{group}/{user}/removeAdmin")
	public String removeAdmin(@AuthenticationPrincipal User currentUser,
							  @PathVariable Group group,
							  @PathVariable User user) {
		groupService.removeGroupAdmin(currentUser, user, group);
		return "redirect:/groups/" + group.getId() + "/socialList/groupAdmins";
	}
	
	@GetMapping("/groups/{group}/{user}/ban")
	public String banUserInGroup(@AuthenticationPrincipal User currentUser,
						  		 @PathVariable Group group,
						  		 @PathVariable User user) {
		groupService.banUser(currentUser, user, group);
		return "redirect:/groups/" + group.getId() + "/socialList/groupBanList";
	}
	
	@GetMapping("/groups/{group}/{user}/unban")
	public String unbanUserInGroup(@AuthenticationPrincipal User currentUser,
					 	 		   @PathVariable Group group,
					 	 		   @PathVariable User user) {
		groupService.unbanUser(currentUser, user, group);
		return "redirect:/groups/" + group.getId() + "/socialList/groupBanList";
	}
	
	@GetMapping("/groups/{group}/{user}/makeOwner")
	public String makeGroupOwnerAuth(Model model) {
		model.addAttribute("loginAttention", "confirm the action on the group with login and password");
		return "login";
	}
	
	@PostMapping("/groups/{group}/{user}/makeOwner")
	public String makeGroupOwner(@AuthenticationPrincipal User currentUser,
							 	 @RequestParam String username,
							 	 @RequestParam String password,
							 	 @PathVariable Group group,
							 	 @PathVariable User user) {
		
		//password encoder
		groupService.makeOwner(currentUser, user, group, username, password);
		return "redirect:/groups/" + group.getId() + "/socialList/groupAdmins";
	}
	
	@GetMapping("/groups/{group}/socialList/{listType}")
	public String groupConnections(@AuthenticationPrincipal User currentUser,
								   @PathVariable String listType,
							 	   @PathVariable Group group,
							 	   Model model) {
		GroupDto currentGroup = groupService.getOneGroup(currentUser, group);
		model.addAttribute("group", currentGroup);
		model.addAttribute("groupSubscrabers", group.getGroupSubs());
		model.addAttribute("groupAdmins", group.getGroupAdmins());
		model.addAttribute("groupBanList", group.getGroupBanList());
		model.addAttribute("listType", listType);
		return "groupConnections";
	}
	
}