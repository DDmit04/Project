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
import com.web.data.dto.GroupDto;
import com.web.data.dto.PostDto;
import com.web.data.dto.UserDto;
import com.web.service.GroupService;
import com.web.service.PostService;
import com.web.service.UserProfileService;
import com.web.service.UserService;


@Controller
public class UserProfileController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserProfileService userProfileService;
	
	@Autowired
	private GroupService groupService;
	
	
	@GetMapping("{user}/profile")
	public String getUserProfile(@AuthenticationPrincipal User currentUser,
								 @PathVariable User user,
								 Model model) {
		Iterable<PostDto> searchByPostAuthor = postService.findPostsByUser(currentUser, user);
		UserDto userProfile = userService.findOneToUser(currentUser, user);
		model.addAttribute("user", userProfile);
		model.addAttribute("posts", searchByPostAuthor);
		model.addAttribute("userGroups", user.getSubedGroups());		
		return "userProfile";
	}
	
	@PostMapping("{user}/profile")
	public String addPostUserProfile(@AuthenticationPrincipal User currentUser,
									 @PathVariable User user,
							  		 @RequestParam String postText, 
							  		 @RequestParam String tags,
							  		 @RequestParam("file") MultipartFile file,
							  		 Model model) throws IllegalStateException, IOException {
		Iterable<PostDto> searchByPostAuthor = postService.findPostsByUser(currentUser, user);
		postService.addPost(postText, tags, file, currentUser);
		UserDto userProfile = userService.findOneToUser(currentUser, user);
		model.addAttribute("user", userProfile);
		model.addAttribute("posts", searchByPostAuthor);		
		return "redirect:/" + user.getId() + "/profile";
	}
	
	
	@GetMapping("{user}/profile/redact")
	public String redactProfile(@AuthenticationPrincipal User currentUser,
								@PathVariable User user,
								Model model) {
		UserDto currentUserProfile = userService.findOneToStatistic(currentUser);
		model.addAttribute("user", currentUserProfile);
		model.addAttribute("blackList", currentUser.getBlackList());
		return "userRedaction";
	}
	
	@PostMapping("{user}/profile/redact")
	public String redactProfile(@AuthenticationPrincipal User currentUser,
								@PathVariable User user,
							  	@RequestParam(required = false) String userTitle,
							  	@RequestParam(required = false) String userInformation, 
							  	@RequestParam("file") MultipartFile file,
							  	Model model) throws IllegalStateException, IOException {
		UserDto currentUserProfile = userService.findOneToStatistic(currentUser);
		userProfileService.updateUserProfile(currentUser, file, userInformation, userTitle);
		model.addAttribute("user", currentUserProfile);
		return "redirect:/profile/redact";
	}

	@GetMapping("{user}/subscribe")
	public String subscribe(@AuthenticationPrincipal User currentUser,
							@PathVariable User user) {
		userProfileService.addSubscription(user, currentUser);
		return "redirect:/" + user.getId() + "/profile";
	}
	
	@GetMapping("{user}/unsubscribe")
	public String unsubscribe(@AuthenticationPrincipal User currentUser,
							  @PathVariable User user) {
		userProfileService.removeSubscription(user, currentUser);
		return "redirect:/" + user.getId() + "/profile";
	}
	
	@GetMapping("{user}/{currentUser}/inBlackList")
	public String addInBlackList(@AuthenticationPrincipal User usr,
								 @PathVariable User currentUser,
								 @PathVariable("user") User bannedUser) {
		if(usr.equals(currentUser)) {
			userProfileService.addInBlackList(bannedUser, currentUser);
		}
		return "redirect:/" + currentUser.getId() + "/profile/socialList/blackList";
	}
	
	@GetMapping("{user}/{currentUser}/fromBlackList")
	public String removeFromBlackList(@AuthenticationPrincipal User usr,
									  @PathVariable User currentUser,
							  		  @PathVariable("user") User bannedUser) {
		if(currentUser.equals(usr)) {
			userProfileService.removeFromBlackList(bannedUser, currentUser);
		}
		return "redirect:/" + currentUser.getId() + "/profile/socialList/blackList";
	}
	
	@GetMapping("/{user}/profile/socialList/{listType}")
	public String subList(@PathVariable User user,
					      @PathVariable String listType,
						  Model model) {
		Iterable<GroupDto> groups = groupService.findUserGroupsDto(user);
		UserDto usr = userService.findOneUserToList(user);
		model.addAttribute("user", usr);
		model.addAttribute("friends", user.getUserFriends());
		model.addAttribute("subscriptions", user.getSubscriptions());
		model.addAttribute("subscribers", user.getSubscribers());
		model.addAttribute("userBlackList", user.getBlackList());
		model.addAttribute("groups", groups);
		model.addAttribute("listType", listType);
		return "userConnections";
	}
}
