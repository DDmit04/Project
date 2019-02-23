package com.web.controllers;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.web.repository.UserRepo;
import com.web.service.PostService;

@Controller
public class MainController {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PostService postService;
	
	@GetMapping("/")
	public String greetingPage(Model model) {
		return "login";
	}
	
	@GetMapping("/registration")
	public String userRegistration() {
		return "registrationForm";
	}
	
	@PostMapping("/registration")
	public String addNewUser(@RequestParam String username, 
			   				 @RequestParam String password,
			   				 User user,
			   				 Model model) {
		User userFromDb = userRepo.findByUsername(user.getUsername());
		if(userFromDb != null) {
			model.addAttribute("userError" , true);
			return "registrationForm";
		} else {
			user.setActive(true);
			user.setRoles(Collections.singleton(UserRoles.USER));
			userRepo.save(user);
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
	public String addPost(@RequestParam String text, 
						  @RequestParam String tags,
						  @RequestParam(required = false) String search,
						  @RequestParam("file") MultipartFile file,
						  Post post,
						  Model model) throws IllegalStateException, IOException {
		postService.updatePost(post, text, tags, file);
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