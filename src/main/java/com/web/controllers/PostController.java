package com.web.controllers;

import java.io.IOException;
import java.util.Set;

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
import com.web.service.PostService;

@Controller
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@GetMapping("/")
	public String greetingPage(Model model) {
		return "redirect:/posts";
	}
	
	@GetMapping("/posts")
	public String getPosts(@AuthenticationPrincipal User currentUser,
						   @RequestParam(required = false) String search,
						   Model model) {
		Iterable<PostDto> searchByTag = postService.searchPostsByTag(search, currentUser);
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
		return "postList";	
	}
	
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
	
	@GetMapping("posts/{post}/like")
	public String likePost(@AuthenticationPrincipal User currentUser,
						   @PathVariable Post post,
						   RedirectAttributes redirectAttributes,
						   @RequestHeader(required = false) String referer) {
		Set<User> like = post.getPostLikes();
		if(like.contains(currentUser)) {
			like.remove(currentUser);
		} else {
			like.add(currentUser);
		}
		UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();
		components.getQueryParams()
			.entrySet()
			.forEach(pair -> redirectAttributes.addAttribute(pair.getKey(), pair.getValue()));
		return "redirect:" + components.getPath();	
	}
}