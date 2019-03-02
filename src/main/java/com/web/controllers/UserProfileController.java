package com.web.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.web.data.FrendReqest;
import com.web.data.User;
import com.web.data.dto.PostDto;
import com.web.data.dto.UserDto;
import com.web.repository.FrendReqestRepo;
import com.web.repository.UserRepo;
import com.web.service.PostService;

@Controller
public class UserProfileController {
	
	@Autowired
	private PostService postService;

	@Autowired
	private FrendReqestRepo frendReqestRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@GetMapping("/frendRequest")
	public String userFrendRequest(@AuthenticationPrincipal User currentUser,
								   Model model) {
		Iterable<FrendReqest> frendReqestsFrom = frendReqestRepo.findByReqiestFrom(currentUser);
		Iterable<FrendReqest> frendReqestsTo = frendReqestRepo.findByReqiestTo(currentUser);
		model.addAttribute("frendReqestsFrom", frendReqestsFrom);
		model.addAttribute("frendReqestsTo", frendReqestsTo);
		return "frendReqestList";
	}
	
//	@GetMapping("{user}/frendRequest")
//	public String frendRequest(@AuthenticationPrincipal User currentUser,
//							   @PathVariable User user,
//							   Model model) {
//		return null;
//	}
	
	@GetMapping("{user}/frendRequest")
	public String addFrendRequest(@AuthenticationPrincipal User currentUser,
			   					  @PathVariable User user,
			   					  @RequestParam(required = false) String frendReqestText,
			   					  Model model) {
		FrendReqest frendReqest = new FrendReqest(" ", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		frendReqest.setReqiestFrom(currentUser);
		frendReqest.setReqiestTo(user);
		frendReqestRepo.save(frendReqest);
		return "redirect:/" + user.getId() + "/profile";
	}
	
	@GetMapping("{user}/frendRequest/{frendReqest}/accept")
	public String acceptFrendRequest(@AuthenticationPrincipal User currentUser,
									 @PathVariable FrendReqest frendReqest,
			   						 @PathVariable User user,
			   						 Model model) {
		currentUser.getUserFriends().add(user);
		userRepo.save(user);
		user.getUserFriends().add(currentUser);
		userRepo.save(currentUser);
		frendReqestRepo.delete(frendReqest);
		return "redirect:/frendRequest";
	}
	
	@GetMapping("{user}/frendRequest/{frendReqest}/denial")
	public String denialFrendRequest(@AuthenticationPrincipal User currentUser,
									 @PathVariable FrendReqest frendReqest,
									 @PathVariable User user,
									 Model model) {
		frendReqestRepo.delete(frendReqest);
		return "redirect:/frendRequest";
	}
	
	@GetMapping("{user}/prifile/frendlist")
	public String getFrendlist(@AuthenticationPrincipal User currentUser,
							   @PathVariable User user,
							   Model model) {
		Iterable<User> userFrends = user.getUserFriends();
		model.addAttribute("frends", userFrends);
		return "frendList";
	}
	
	@GetMapping("{user}/profile")
	public String getUserProfile(@AuthenticationPrincipal User currentUser,
								 @PathVariable User user,
								 Model model) {
		Iterable<PostDto> searchByPostAuthor = postService.findPostsByUser(currentUser, user);
		UserDto userProfile = userRepo.findOneUser(user, user.getId());
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
		UserDto userProfile = userRepo.findOneUser(user, user.getId());
		model.addAttribute("user", userProfile);
		model.addAttribute("posts", searchByPostAuthor);		
		return "redirect:/" + user.getId() + "/profile";
	}

}
