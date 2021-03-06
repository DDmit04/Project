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
import com.web.api.user.UserProfileService;
import com.web.api.user.UserService;
import com.web.data.Post;
import com.web.data.User;
import com.web.data.dto.GroupDto;
import com.web.data.dto.PostDto;
import com.web.data.dto.UserDto;
import com.web.service.GroupServiceImpl;
import com.web.service.PostServiceImpl;
import com.web.service.UserProfileServiceImpl;
import com.web.service.UserServiceImpl;


@Controller
public class UserProfileController {
	
	private PostService postService;
	private GroupService groupService;
	private UserService userService;
	private UserProfileService userProfileService;
	
	@Autowired
	public UserProfileController(UserServiceImpl userServiceImpl, UserProfileServiceImpl userProfileServiceImpl, 
								 GroupServiceImpl groupService, PostServiceImpl postService) {
		this.userService = userServiceImpl;
		this.userProfileService = userProfileServiceImpl;
		this.groupService = groupService;
		this.postService = postService;
	}
	
	@GetMapping("{user}/profile")
	public String getUserProfile(@AuthenticationPrincipal User currentUser,
								 @PathVariable User user,
								 Model model) {
		Iterable<PostDto> searchByPostAuthor = postService.getUserPosts(currentUser, user);
		UserDto userProfile = userService.getOneUserToUser(user, currentUser);
		model.addAttribute("user", userProfile);
		model.addAttribute("posts", searchByPostAuthor);
		model.addAttribute("userGroups", user.getSubedGroups());	
		return "userProfile";
	}
	
	@PostMapping("{user}/profile")
	public String addPostUserProfile(@AuthenticationPrincipal User currentUser,
									 @PathVariable User user,
							  		 @RequestParam("file") MultipartFile file,
							  		 Model model,
							  		 Post post) throws IllegalStateException, IOException {
		Iterable<PostDto> searchByPostAuthor = postService.getUserPosts(currentUser, user);
		postService.createPost(currentUser, post, file);
		UserDto userProfile = userService.getOneUserToUser(user, currentUser);
		model.addAttribute("user", userProfile);
		model.addAttribute("posts", searchByPostAuthor);		
		return "redirect:/" + user.getId() + "/profile";
	}
	
	
	@GetMapping("{user}/profile/redact")
	public String redactProfile(@AuthenticationPrincipal User currentUser,
								@PathVariable User user,
								Model model) {
		if(user.equals(currentUser)) {
			UserDto currentUserProfile = userService.getOneUserToStatistic(currentUser);
			model.addAttribute("user", currentUserProfile);
			return "userOrGroupRedaction";
		} else {
			return "redirect:/" + user.getId() + "/profile";
		}
	}
	
	@PostMapping("{user}/profile/redact")
	public String redactProfile(@AuthenticationPrincipal User currentUser,
								@PathVariable User user,
							  	@RequestParam(required = false) String userStatus,
							  	@RequestParam(required = false) String userInformation, 
							  	@RequestParam("file") MultipartFile file,
							  	Model model) throws IllegalStateException, IOException {
		userProfileService.updateUserProfile(currentUser ,user, file, userInformation, userStatus);
		UserDto currentUserProfile = userService.getOneUserToStatistic(user);
		model.addAttribute("user", currentUserProfile);
		return "redirect:/" + user.getId() + "/profile/redact";
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
		Iterable<GroupDto> groups = groupService.getUserGroups(user);
		UserDto usr = userService.getOneUserToList(user);
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
