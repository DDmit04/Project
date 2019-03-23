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

import com.web.data.Comment;
import com.web.data.Post;
import com.web.data.User;
import com.web.data.dto.PostDto;
import com.web.service.CommentService;
import com.web.service.PostService;

@Controller
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private PostService postService;
	
	@GetMapping("{post}/comments")
	public String getComments(@AuthenticationPrincipal User currentUser,
							  @PathVariable Post post,
							  Model model) {
		Iterable<Comment> searchByCommentedPost = commentService.findCommentsByCommentedPost(post);
		PostDto commentedPost = postService.findOnePost(currentUser, post);
		model.addAttribute("comments", searchByCommentedPost);
		model.addAttribute("post", commentedPost);
		return "commentList";
	}
	
	@PostMapping("{post}/comments")
	public String addComment(@AuthenticationPrincipal User currentUser,
							 @PathVariable Post post,
							 @RequestParam String commentText,
							 @RequestParam("commentPic") MultipartFile commentPic,
							 Comment comment,
			  				 Model model) throws IllegalStateException, IOException {
		commentService.addComment(post, commentText, currentUser, commentPic);
		return "redirect:/" + post.getId() + "/comments";
	}
	
	@GetMapping("{post}/comments/{comment}/delete")
	public String deleteComment(@AuthenticationPrincipal User currentUser,
							    @PathVariable Post post,
								@PathVariable Comment comment,
								Model model) {
		if(comment.getCommentAuthor().equals(currentUser)) {
			commentService.deleteComment(comment);
		}
		return "redirect:/" + post.getId() + "/comments";
	}
	
	@GetMapping("{post}/comments/{comment}/edit")
	public String getEditComment(@AuthenticationPrincipal User currentUser,
								 @PathVariable Post post,
							     @PathVariable Comment comment,
							     Model model) {
		Iterable<Comment> searchByCommentedPost = commentService.findCommentsByCommentedPost(post);
		PostDto commentedPost = postService.findOnePost(currentUser, post);
		model.addAttribute("comments", searchByCommentedPost);
		model.addAttribute("post", commentedPost);		model.addAttribute("editedComment", comment);
		return "commentList";
	}
	
	@PostMapping("{post}/comments/{comment}/edit")
	public String editComment(@AuthenticationPrincipal User currentUser,
							  @PathVariable Post post,
							  @PathVariable Comment comment,
							  @RequestParam("commentPic") MultipartFile commentPic,
							  @RequestParam String commentText,
							  Model model) throws IllegalStateException, IOException {
		if(comment.getCommentAuthor().equals(currentUser)) {
			commentService.updateComment(comment, commentText, commentPic);
		}
		return "redirect:/" + post.getId() + "/comments";
	}

}
