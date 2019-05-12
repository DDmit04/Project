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
import com.web.api.ImageService;
import com.web.api.post.PostService;
import com.web.api.user.UserService;
import com.web.data.Group;
import com.web.data.User;
import com.web.data.dto.GroupDto;
import com.web.data.dto.ImageDto;
import com.web.data.dto.PostDto;
import com.web.data.dto.UserDto;
import com.web.service.GroupServiceImpl;
import com.web.service.ImageServiceImpl;
import com.web.service.PostServiceImpl;
import com.web.service.UserServiceImpl;

@Controller
public class GroupAlbumController {
	
	private PostService postService;
	private GroupService groupService;
	private UserService userService;
	private ImageService imageService;
	
	@Autowired
	public GroupAlbumController(PostServiceImpl postService, GroupServiceImpl groupService, UserServiceImpl userService,
						   ImageServiceImpl imageService) {
		this.postService = postService;
		this.groupService = groupService;
		this.userService = userService;
		this.imageService = imageService;
	}

	@GetMapping("/groups/{group}/album")
	public String getGroupAlbum(@AuthenticationPrincipal User currentUser,
							    @PathVariable Group group,
							    Model model) {
		UserDto oneUser = userService.getOneUserToGroup(currentUser, group);
		Iterable<GroupDto> adminedGroups = groupService.getAdminedGroups(currentUser);
		Iterable<PostDto> groupPosts = postService.getGroupPosts(currentUser, group);
		Iterable<ImageDto> groupImages = imageService.findByImgGroup(currentUser, group);
		model.addAttribute("images", groupImages);
		model.addAttribute("posts", groupPosts);
		model.addAttribute("currentGroup", group);
		model.addAttribute("groupSubs", group.getGroupSubs());
		model.addAttribute("user", oneUser);
		model.addAttribute("adminedGroups", adminedGroups);
		return "album";
	}
	
	@PostMapping("/groups/{group}/album")
	public String uploadImageGroupAlbum(@AuthenticationPrincipal User currentUser,
								   	   @PathVariable Group group,
								   	   @RequestParam("file") MultipartFile file,
								   	   Model model) throws IllegalStateException, IOException {
		if((group.getGroupOwner().equals(currentUser) || group.getGroupAdmins().contains(currentUser))
				&& file != null && !file.isEmpty()) {
			groupService.uploadGroupPic(group, file);
		}
		return "redirect:/groups/" + group.getId() + "/album";
	}

}
