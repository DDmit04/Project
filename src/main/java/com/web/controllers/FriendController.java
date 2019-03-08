package com.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.web.data.FriendRequest;
import com.web.data.User;
import com.web.data.dto.UserDto;
import com.web.service.ProfileService;
import com.web.service.UserService;

@Controller
public class FriendController {
	
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
		profileService.deleteFriendRequest(friendRequest);
		return "redirect:/friendRequest";
	}
	
	@GetMapping("{user}/profile/friendlist")
	public String getFriendlist(@PathVariable User user,
							    Model model) {
		UserDto userProfile = userService.findOneUser(user);
		Iterable<User> userFrends = user.getUserFriends();
		model.addAttribute("user", userProfile);
		model.addAttribute("friends", userFrends);
		return "friendList";
	}

}