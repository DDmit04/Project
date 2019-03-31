package com.web.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web.data.User;
import com.web.data.dto.GroupDto;
import com.web.data.dto.PostDto;
import com.web.data.dto.UserDto;
import com.web.exceptions.UserException;
import com.web.service.GroupService;
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
		model.addAttribute("userGroups", user.getUserFriends());		
		return "userProfile";
	}
	
	@PostMapping("{user}/profile")
	public String addPostUserProfile(@AuthenticationPrincipal User currentUser,
							  		 @RequestParam String postText, 
							  		 @RequestParam String tags,
							  		 @RequestParam("file") MultipartFile file,
							  		 @PathVariable User user,
							  		 Model model) throws IllegalStateException, IOException {
		Iterable<PostDto> searchByPostAuthor = postService.findPostsByUser(currentUser, user);
		postService.addPost(postText, tags, file, currentUser);
		UserDto userProfile = userService.findOneToUser(currentUser, user);
		model.addAttribute("user", userProfile);
		model.addAttribute("posts", searchByPostAuthor);		
		return "redirect:/" + user.getId() + "/profile";
	}
	
	@GetMapping("profile/redact")
	public String redactProfile(@AuthenticationPrincipal User currentUser,
								Model model) {
		UserDto currentUserProfile = userService.findOneToStatistic(currentUser);
		model.addAttribute("user", currentUserProfile);
		model.addAttribute("blackList", currentUser.getBlackList());
		return "userRedaction";
	}
	
	@PostMapping("profile/redact")
	public String redactProfile(@AuthenticationPrincipal User currentUser,
							  	@RequestParam(required = false) String userTitle,
							  	@RequestParam(required = false) String userInformation, 
							  	@RequestParam("file") MultipartFile file,
							  	Model model) throws IllegalStateException, IOException {
		UserDto currentUserProfile = userService.findOneToStatistic(currentUser);
		profileService.updateUserProfile(currentUser, file, userInformation, userTitle);
		model.addAttribute("user", currentUserProfile);
		return "redirect:/profile/redact";
	}

	@GetMapping("profile/settings")
	public String settings(@ModelAttribute("redirectMessage") String redirectMessage,
						   Model model) {
		model.addAttribute(redirectMessage);
		return "profileSettings";
	}
	
	@PostMapping("profile/settings")
	public String changePassword(@AuthenticationPrincipal User currentUser,
								 @RequestParam String currentPassword,
								 @RequestParam("password") String newPassword,
							   	 RedirectAttributes redirectAttrs,
								 Model model) {
		try {
			userService.changePassword(currentUser, currentPassword, newPassword);
		} catch (UserException userException) {
			model.addAttribute("currentPasswordError", userException.getMessage());
			return "profileSettings";
		}
		redirectAttrs.addFlashAttribute("redirectMessage", "password seccesfuli changed!");
		return "redirect:/profile/settings";
	}
	
	@PostMapping("/profile/settings/deleteAccount")
	public String deleteAccount(@AuthenticationPrincipal User currentUser,
								@RequestParam String accountDeletePassword,
								HttpServletRequest request,
								Model model) throws ServletException {
		try {
			userService.deleteUser(currentUser, accountDeletePassword);
			request.logout();
		} catch (UserException userException) {
			model.addAttribute("accountDeleteError", userException.getMessage());
			return "profileSettings";
		}
		return "redirect:/login";
	}
	
	@GetMapping("{user}/subscribe")
	public String subscribe(@AuthenticationPrincipal User currentUser,
							@PathVariable User user) {
		profileService.addSubscription(user, currentUser);
		return "redirect:/" + user.getId() + "/profile";
	}
	
	@GetMapping("{user}/unsubscribe")
	public String unsubscribe(@AuthenticationPrincipal User currentUser,
							  @PathVariable User user) {
		profileService.removeSubscription(user, currentUser);
		return "redirect:/" + user.getId() + "/profile";
	}
	
	@GetMapping("{user}/{currentUser}/inBlackList")
	public String addInBlackList(@AuthenticationPrincipal User usr,
								 @PathVariable User currentUser,
								 @PathVariable("user") User bannedUser) {
		if(usr.equals(currentUser)) {
			profileService.addInBlackList(bannedUser, currentUser);
		}
		return "redirect:/" + currentUser.getId() + "/profile/socialList/blackList";
	}
	
	@GetMapping("{user}/{currentUser}/fromBlackList")
	public String removeFromBlackList(@AuthenticationPrincipal User usr,
									  @PathVariable User currentUser,
							  		  @PathVariable("user") User bannedUser) {
		if(currentUser.equals(usr)) {
			profileService.removeFromBlackList(bannedUser, currentUser);
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
