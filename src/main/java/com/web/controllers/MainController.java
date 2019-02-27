package com.web.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.web.data.Comment;
import com.web.data.Post;
import com.web.data.User;
import com.web.exceptions.UserException;
import com.web.repository.CommentRepo;
import com.web.service.CommentService;
import com.web.service.PostService;
import com.web.service.UserService;

@Controller
public class MainController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private PostService postService;
	
	@GetMapping("{post}/comments")
	public String getComments(@PathVariable Post post,
							  Model model) {
		Iterable<Comment> searchByCommentedPost = commentService.findCommentsByCommentedPost(post);
		model.addAttribute("comments", searchByCommentedPost);
		model.addAttribute("post", post);
		return "commentList";
	}
	
	@PostMapping("{post}/comments")
	public String addComment(@AuthenticationPrincipal User currentUser,
							 @PathVariable Post post,
							 @RequestParam String commentText,
							 Comment comment,
			  				 Model model) {
		commentService.addComment(post, commentText, currentUser);
		model.addAttribute("post", post);
		return "redirect:/" + post.getId() + "/comments";
	}
	
	@GetMapping("{post}/comments/{comment}/delete")
	public String deleteComment(@PathVariable Post post,
								@PathVariable Comment comment,
								Model model) {
		return null;
	}
	
	@GetMapping("{post}/comments/{comment}/edit")
	public String getEditComment(@PathVariable Post post,
							     @PathVariable Comment comment,
							     Model model) {
		return null;
	}
	
	@PostMapping("{post}/comments/{comment}/edit")
	public String editComment(@PathVariable Post post,
							  @PathVariable Comment comment,
							  Model model) {
		return null;
	}
	
	@GetMapping("{user}/profile")
	public String getUserPrifile(
								 @PathVariable User user,
								 Model model) {
		Iterable<Post> searchByPostAuthor = postService.findPostsByUser(user);
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
		Iterable<Post> searchByPostAuthor = postService.findPostsByUser(user);
		postService.addPost(postText, tags, file, currentUser);
		model.addAttribute("user", user);
		model.addAttribute("posts", searchByPostAuthor);		
		return "redirect:/" + user.getId() + "/profile";
	}
	
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
			model.addAttribute("registrationName", e.getUser().getUsername());
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
	public String addPost(@AuthenticationPrincipal User currentUser,
						  @RequestParam String postText, 
						  @RequestParam String tags,
						  @RequestParam(required = false) String search,
						  @RequestParam("file") MultipartFile file,
						  Model model) throws IllegalStateException, IOException {
		postService.addPost(postText, tags, file, currentUser);
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
					       @RequestParam String postText,
						   @RequestParam String tags, 
						   @RequestParam("file") MultipartFile file,
						   Model model) throws IllegalStateException, IOException {
		postService.updatePost(post, postText, tags, file);
		return "redirect:/posts";
	}
	
	@GetMapping("{post}/delete")
	public String deletePost(@PathVariable Post post) {
		postService.deletePost(post);
		return "redirect:/posts";
	}
}