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

import com.web.data.FriendRequest;
import com.web.data.User;
import com.web.data.dto.PostDto;
import com.web.data.dto.UserDto;
import com.web.service.PostService;
import com.web.service.ProfileService;
import com.web.service.UserService;

@Controller
public class UserProfileController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProfileService profileService;
	
	@GetMapping("/friendRequest")
	public String userFriendRequest(@AuthenticationPrincipal User currentUser,
								   Model model) {
		Iterable<FriendRequest> friendReqestsFrom = profileService.findRequestFrom(currentUser);
		Iterable<FriendRequest> friendReqestsTo = profileService.findRequestTo(currentUser);
		model.addAttribute("friendRequestsFrom", friendReqestsFrom);
		model.addAttribute("friendRequestsTo", friendReqestsTo);
		return "friendRequestList";
	}
	
	@GetMapping("{user}/friendRequest")
	public String addFriendRequest(@AuthenticationPrincipal User currentUser,
			   					   @PathVariable User user,
			   					   Model model) {
		profileService.addFriendRequest(user, currentUser);
		return "redirect:/" + user.getId() + "/profile";
	}
	
	@GetMapping("{user}/friendRequest/{friendRequest}/accept")
	public String acceptFriendRequest(@AuthenticationPrincipal User currentUser,
									  @PathVariable FriendRequest friendRequest,
									  @PathVariable User user,
									  Model model) {
		profileService.addFriend(user, currentUser, friendRequest);
		return "redirect:/friendRequest";
	}
	
	@GetMapping("/{user}/deleteFriend")
	public String deleteFriend(@AuthenticationPrincipal User currentUser,
							   @PathVariable User user) {
		profileService.deleteFriend(user, currentUser);
		return "redirect:/" + user.getId() + "/profile";
	}
	
	@GetMapping("{user}/friendRequest/{friendRequest}/denial")
	public String denialFriendRequest(@AuthenticationPrincipal User currentUser,
									  @PathVariable FriendRequest friendRequest,
									  @PathVariable User user,
									  Model model) {
		profileService.deleteRequest(friendRequest);
		return "redirect:/friendRequest";
	}
	
	@GetMapping("{user}/profile/friendlist")
	public String getFriendlist(@AuthenticationPrincipal User currentUser,
							    @PathVariable User user,
							    Model model) {
		Iterable<User> userFrends = user.getUserFriends();
		model.addAttribute("user", user);
		model.addAttribute("userFriendsCount", user.getUserFriends().size());
		model.addAttribute("friends", userFrends);
		return "friendList";
	}
	
	@GetMapping("{user}/profile")
	public String getUserProfile(@AuthenticationPrincipal User currentUser,
								 @PathVariable User user,
								 Model model) {
		Iterable<PostDto> searchByPostAuthor = postService.findPostsByUser(currentUser, user);
		UserDto userProfile = userService.findOneUser(currentUser, user);
		model.addAttribute("user", userProfile);
		model.addAttribute("posts", searchByPostAuthor);		
		return "userProfile";
	}
	
	@PostMapping("{user}/profile")
	public String userProfile(@AuthenticationPrincipal User currentUser,
							  @RequestParam String postText, 
							  @RequestParam String tags,
							  @RequestParam("file") MultipartFile file,
							  @PathVariable User user,
			 				  Model model) throws IllegalStateException, IOException {
		Iterable<PostDto> searchByPostAuthor = postService.findPostsByUser(currentUser, user);
		postService.addPost(postText, tags, file, currentUser);
		UserDto userProfile = userService.findOneUser(currentUser, user);
		model.addAttribute("user", userProfile);
		model.addAttribute("posts", searchByPostAuthor);		
		return "redirect:/" + user.getId() + "/profile";
	}

}
