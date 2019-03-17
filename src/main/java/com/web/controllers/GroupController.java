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

import com.web.data.UserGroup;
import com.web.data.dto.UserGroupDto;
import com.web.data.User;
import com.web.repository.PostRepo;
import com.web.repository.UserGroupRepo;
import com.web.repository.UserRepo;
import com.web.service.GroupService;
import com.web.service.PostService;
import com.web.utils.DateUtil;

@Controller
public class GroupController {
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private UserGroupRepo userGroupRepo;
	
	@GetMapping("/groups")
	public String getAllGroups(@AuthenticationPrincipal User currentUser,
							   Model model) {
		Iterable<UserGroup> allGroups = userGroupRepo.findAll();
		model.addAttribute("groups", allGroups);
		return "groupList";
	}
	
	@GetMapping("/groups/{group}")
	public String getAllGroups(@AuthenticationPrincipal User currentUser,
							   @PathVariable UserGroup group,
							   Model model) {
		UserGroupDto oneGroup = userGroupRepo.findOneGroup(currentUser, group.getId());
		model.addAttribute("posts", postRepo.findGroupPosts(currentUser, group));
		model.addAttribute("group", oneGroup);
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
		return "redirect:/groups" + group.getId();
	}
	
//	@GetMapping("/groups/{group}")
//	public String getGroup(@AuthenticationPrincipal User currentUser,
//						   @PathVariable UserGroup group,
//						   Model model) {
//		Iterable<UserGroup> allGroups = userGroupRepo.findAll();
//		model.addAttribute("groups", allGroups);
//		return "groupList";
//	}

	@GetMapping("{user}/groups")
	public String getUserGroups(@AuthenticationPrincipal User currentUser,
								@PathVariable User user,
								Model model) {
		model.addAttribute("groups", user.getSubedGroups());
		model.addAttribute("user", currentUser);
		return "groupList";
	}
	
	@GetMapping("{user}/groups/create")
	public String createGroup() {
		return "createGroupForm";
	}
	
	@PostMapping("{user}/groups/create")
	public String createGroup(@AuthenticationPrincipal User currentUser,
							  @RequestParam String groupName,
							  @RequestParam (required = false)String groupInformation,
							  Model model) {
		groupService.createGroup(groupName, groupInformation, currentUser);
		return "redirect:/" + currentUser.getId() + "/groups";
	}
	
	@GetMapping("/groups/{group}/sub")
	public String subGroup(@AuthenticationPrincipal User currentUser,
						   @PathVariable UserGroup group) {
		groupService.addGroupSub(group, currentUser);
		return "redirect:/groupList";
	}
	
	@GetMapping("/groups/{group}/unsub")
	public String unsubGroup(@AuthenticationPrincipal User currentUser,
							 @PathVariable UserGroup group) {
		groupService.removeGroupSub(group, currentUser);
		return "redirect:/groupList";
	}
	
//	@GetMapping("/groups/{group}/sub")
//	public String addAdminGroup(@AuthenticationPrincipal User currentUser,
//						   @PathVariable UserGroup group) {
//		groupService.addGroupAdmin(group, currentUser);
//		return "redirect:/groupList";
//	}
//	
//	@GetMapping("/groups/{group}/unsub")
//	public String removeAdminGroup(@AuthenticationPrincipal User currentUser,
//							 @PathVariable UserGroup group) {
//		groupService.removeGroupAdmin(group, currentUser);
//		return "redirect:/groupList";
//	}
	
}