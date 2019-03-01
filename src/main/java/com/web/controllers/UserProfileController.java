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

import com.web.data.Post;
import com.web.data.User;
import com.web.data.dto.PostDto;
import com.web.service.PostService;

@Controller
public class UserProfileController {
	
	@Autowired
	private PostService postService;
	
	@GetMapping("{user}/profile")
	public String getUserPrifile(@AuthenticationPrincipal User currentUser,
								 @PathVariable User user,
								 Model model) {
		Iterable<PostDto> searchByPostAuthor = postService.findPostsByUser(currentUser, user);
		model.addAttribute("user", user);
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
		model.addAttribute("user", user);
		model.addAttribute("posts", searchByPostAuthor);		
		return "redirect:/" + user.getId() + "/profile";
	}

}
