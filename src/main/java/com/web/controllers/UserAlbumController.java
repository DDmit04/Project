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
import com.web.api.user.UserProfileService;
import com.web.api.user.UserService;
import com.web.data.User;
import com.web.data.dto.GroupDto;
import com.web.data.dto.ImageDto;
import com.web.data.dto.UserDto;
import com.web.service.GroupServiceImpl;
import com.web.service.ImageServiceImpl;
import com.web.service.UserProfileServiceImpl;
import com.web.service.UserServiceImpl;

@Controller
public class UserAlbumController {
	
	private GroupService groupService;
	private UserService userService;
	private ImageService imageService;
	private UserProfileService userProfileService;
	
	@Autowired
	public UserAlbumController(GroupServiceImpl groupService, UserServiceImpl userService, ImageServiceImpl imageService,
			UserProfileServiceImpl userProfileService) {
		this.groupService = groupService;
		this.userService = userService;
		this.imageService = imageService;
		this.userProfileService = userProfileService;
	}

	@GetMapping("{user}/profile/album")
	public String getUserAlbum(@AuthenticationPrincipal User currentUser,
							   @PathVariable User user,
							   Model model) {
		UserDto userProfile = userService.getOneUserToUser(user, currentUser);
		Iterable<ImageDto> userImages = imageService.findByImgOwner(currentUser, user);
		Iterable<GroupDto> adminedGroups = groupService.getAdminedGroups(currentUser);
		model.addAttribute("adminedGroups", adminedGroups);
		model.addAttribute("images", userImages);
		model.addAttribute("viewedUser", userProfile);
		return "album";
	}
	
	@PostMapping("{user}/profile/album")
	public String uploadImageUserAlbum(@AuthenticationPrincipal User currentUser,
								   	   @PathVariable User user,
								   	   @RequestParam("file") MultipartFile file,
								   	   Model model) throws IllegalStateException, IOException {
		if(currentUser.equals(user) && file != null && !file.isEmpty()) {
			userProfileService.uploadUserPic(user, file);
		}
		return "redirect:/" + user.getId() + "/profile/album";
	}
	
}
