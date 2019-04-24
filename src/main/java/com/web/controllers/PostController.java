package com.web.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.web.data.Post;
import com.web.data.User;
import com.web.data.dto.PostDto;
import com.web.service.PostServiceImpl;

@Controller
public class PostController {
	
	@Autowired
	private PostServiceImpl postService;
	
	@GetMapping("/")
	public String greetingPage(Model model) {
		return "redirect:/posts";
	}
	
	@GetMapping("/posts")
	public String getPosts(@AuthenticationPrincipal User currentUser,
						   Model model) {
		Iterable<PostDto> searchByTag = postService.getAllPosts(currentUser);
		model.addAttribute("user", currentUser);
		model.addAttribute("posts", searchByTag);
		return "postList";
	}
	
	@PostMapping(value= {"/posts", "/subscriptionPosts"})
	public String addPost(@AuthenticationPrincipal User currentUser,
						  @RequestParam String postText, 
						  @RequestParam String tags,
						  @RequestParam("file") MultipartFile file,
						  Model model) throws IllegalStateException, IOException {
		postService.addUserPost(postText, tags, file, currentUser);
		return "redirect:/posts";
	}
	
	@GetMapping("{post}/edit")
	public String getEditedPost(@AuthenticationPrincipal User currentUser,
						  @PathVariable Post post, 
			  			  Model model) {
		PostDto editedPost = postService.getOnePost(currentUser, post);
		model.addAttribute("post", editedPost);
		return "postEdit";	
	}
	
	@PostMapping("{post}/edit")
	public String editPost(@AuthenticationPrincipal User currentUser,
						   @PathVariable Post post, 
					       @RequestParam String postText,
						   @RequestParam String tags, 
						   @RequestParam("file") MultipartFile file,
						   Model model) throws IllegalStateException, IOException {
		postService.updatePost(currentUser, post, postText, tags, file);
		return "redirect:/posts";
	}
	
	@GetMapping("{post}/delete")
	public String deletePost(@AuthenticationPrincipal User currentUser,
							 @PathVariable Post post) {
		postService.deletePost(currentUser, post);
		return "redirect:/posts";
	}
	
	@GetMapping("{post}/removeRepost")
	public String removeRepost(@AuthenticationPrincipal User currentUser,
							   @PathVariable Post post,
			 				   RedirectAttributes redirectAttributes,
			 				   @RequestHeader(required = false) String referer) {
		postService.removeRepost(currentUser, post);
		return "redirect:/" + post.getId() + "/edit" ;		
	}
	
	@GetMapping("posts/{post}/{user}/like")
	public String likePost(@AuthenticationPrincipal User currentUser,
						   @PathVariable User user,
						   @PathVariable Post post,
						   RedirectAttributes redirectAttributes,
						   @RequestHeader(required = false) String referer) {
		postService.like(currentUser, user, post);
		UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();
		components.getQueryParams()
			.entrySet()
			.forEach(pair -> redirectAttributes.addAttribute(pair.getKey(), pair.getValue()));
		return "redirect:" + components.getPath();	
	}
	
	@PostMapping("{repostedPost}/repost")
	public String addRepost(@AuthenticationPrincipal User currentUser,
							@PathVariable Post repostedPost, 
			 				@RequestParam(required = false) String repostText,
			 				@RequestParam String repostTags) throws IllegalStateException, IOException {
		postService.addRepost(currentUser, repostedPost, repostText, repostTags);
		return "redirect:/posts";
	}
	
}