package com.web.controllers;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.web.data.Post;
import com.web.data.User;
import com.web.data.UserRoles;
import com.web.exceptions.UserException;
import com.web.repository.UserRepo;
import com.web.service.PostService;
import com.web.service.UserService;

@Controller
public class MainController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PostService postService;
	
	@GetMapping("/")
	public String greetingPage(Model model) {
		return "redirect:/posts";
	}
	
	@GetMapping("/registration")
	public String userRegistration() {
		return "registrationForm";
	}
	
	@PostMapping("/registration")
	public String addNewUser(@RequestParam("userPic") MultipartFile userPic,
							 User user,
			   				 Model model) throws IllegalStateException, IOException {
		try {
			userService.addUser(user, userPic);
		} catch (UserException e) {
			model.addAttribute("usernameError", e.getMessage());
			model.addAttribute("registrationName" ,e.getUser().getUsername());
			return "registrationForm";
		}
		return "redirect:/posts";
	}
	
	@GetMapping("/posts")
	public String getPosts(@RequestParam(required = false) String search,
						   Model model) {
		Iterable<Post> searchByTag = postService.searchPostsByTag(search);
		model.addAttribute("posts", searchByTag);
		model.addAttribute("search", search);
		return "postList";
	}
	
	@PostMapping("/posts")
	public String addPost(@AuthenticationPrincipal User user,
						  @RequestParam String text, 
						  @RequestParam String tags,
						  @RequestParam(required = false) String search,
						  @RequestParam("file") MultipartFile file,
						  Post post,
						  Model model) throws IllegalStateException, IOException {
		postService.addPost(post, text, tags, file, user);
		model.addAttribute("search", search);
		return "redirect:/posts";
	}
	
	@GetMapping("{post}/edit")
	public String getPost(@PathVariable Post post, 
			  			  Model model) {
		model.addAttribute("post", post);
		return "postList";	}
	
	@PostMapping("{post}/edit")
	public String editPost(@PathVariable Post post, 
					       @RequestParam String text,
						   @RequestParam String tags, 
						   @RequestParam("file") MultipartFile file,
						   Model model) throws IllegalStateException, IOException {
		postService.updatePost(post, text, tags, file);
		return "redirect:/posts";
	}
	
	@GetMapping("{post}/delete")
	public String deletePost(@PathVariable Post post) {
		postService.deletePost(post);
		return "redirect:/posts";
	}
}