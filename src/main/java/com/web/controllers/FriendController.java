package com.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.web.api.user.UserProfileService;
import com.web.data.FriendRequest;
import com.web.data.User;
import com.web.data.dto.FriendRequestDto;

@Controller
public class FriendController {
	
	private UserProfileService profileService;
	
	@Autowired
	public FriendController(UserProfileService profileService) {
		this.profileService = profileService;
	}

	@GetMapping("/friendRequests/{listType}")
	public String userFriendRequest(@AuthenticationPrincipal User currentUser,
									@PathVariable String listType,
								    Model model) {
		Iterable<FriendRequestDto> friendReqestsFrom = profileService.getFriendRequestsFromUser(currentUser);
		Iterable<FriendRequestDto> friendReqestsTo = profileService.getFriendRequestsToUser(currentUser);
		model.addAttribute("friendRequestsFrom", friendReqestsFrom);
		model.addAttribute("friendRequestsTo", friendReqestsTo);
		model.addAttribute("friendRequestsToCount", friendReqestsTo.spliterator().getExactSizeIfKnown());
		model.addAttribute("friendRequestsFromCount", friendReqestsFrom.spliterator().getExactSizeIfKnown());
		model.addAttribute("listType", listType);
		return "friendRequestsList";
	}
	
	@GetMapping("{user}/friendRequest")
	public String addFriendRequest(@AuthenticationPrincipal User currentUser,
			   					   @PathVariable User user,
			   					   Model model) {
		profileService.createFriendRequest(user, currentUser);
		return "redirect:/" + user.getId() + "/profile";
	}
	
	@GetMapping("{user}/friendRequest/{friendRequest}/accept")
	public String acceptFriendRequest(@AuthenticationPrincipal User currentUser,
									  @PathVariable FriendRequest friendRequest,
									  @PathVariable User user,
									  Model model) {
		profileService.addFriend(friendRequest);
		profileService.deleteFriendRequest(user, currentUser, friendRequest);
		return "redirect:/friendRequests/friendRequestsTo";
	}
	
	@GetMapping("/{user}/{currentUser}/deleteFriend")
	public String deleteFriend(@PathVariable User currentUser,
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
		return "redirect:/friendRequests/friendRequestsTo";
	}
}