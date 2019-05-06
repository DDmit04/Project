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

import com.web.api.CommentService;
import com.web.api.GroupService;
import com.web.api.post.PostService;
import com.web.data.Comment;
import com.web.data.Post;
import com.web.data.User;
import com.web.data.dto.CommentDto;
import com.web.data.dto.GroupDto;
import com.web.data.dto.PostDto;
import com.web.service.CommentServiceImpl;
import com.web.service.GroupServiceImpl;
import com.web.service.PostServiceImpl;

@Controller
public class PostCommentController {
	
	private CommentService commentService;
	private PostService postService;
	private GroupService groupService;
	
	@Autowired
	public PostCommentController(CommentServiceImpl commentService, PostServiceImpl postService, GroupServiceImpl groupService) {
		this.commentService = commentService;
		this.postService = postService;
		this.groupService = groupService;
	}

	@GetMapping("{post}/comments")
	public String getPostComments(@AuthenticationPrincipal User currentUser,
							  	  @PathVariable Post post,
							  	  Model model) {
		Iterable<CommentDto> searchByCommentedPost = commentService.getCommentsByCommentedPost(post);
		PostDto commentedPost = postService.getOnePost(currentUser, post);
		Iterable<GroupDto> adminedGroups = groupService.getAdminedGroups(currentUser);
		model.addAttribute("adminedGroups", adminedGroups);
		model.addAttribute("comments", searchByCommentedPost);
		model.addAttribute("post", commentedPost);
		return "commentList";
	}
	
	@PostMapping("{post}/comments")
	public String addPostComment(@AuthenticationPrincipal User currentUser,
							 	 @PathVariable Post post,
							 	 @RequestParam("commentPic") MultipartFile commentPic,
							 	 Comment comment,
							 	 Model model) throws IllegalStateException, IOException {
		commentService.createComment(currentUser, comment, post, commentPic);
		return "redirect:/" + post.getId() + "/comments";
	}
	
	@GetMapping("{post}/comments/{comment}/delete")
	public String deletePostComment(@AuthenticationPrincipal User currentUser,
							    	@PathVariable Post post,
							    	@PathVariable Comment comment,
							    	Model model) {
		commentService.deleteComment(currentUser, post, comment);
		return "redirect:/" + post.getId() + "/comments";
	}
	
	@GetMapping("{post}/comments/{comment}/edit")
	public String getEditComment(@AuthenticationPrincipal User currentUser,
								 @PathVariable Post post,
							     @PathVariable Comment comment,
							     Model model) {
		Iterable<CommentDto> searchByCommentedPost = commentService.getCommentsByCommentedPost(post);
		PostDto commentedPost = postService.getOnePost(currentUser, post);
		Iterable<GroupDto> adminedGroups = groupService.getAdminedGroups(currentUser);
		model.addAttribute("adminedGroups", adminedGroups);
		model.addAttribute("comments", searchByCommentedPost);
		model.addAttribute("post", commentedPost);		
		model.addAttribute("editedComment", comment);
		return "commentEdit";
	}
	
	@PostMapping("{post}/comments/{comment}/edit")
	public String editComment(@AuthenticationPrincipal User currentUser,
							  @PathVariable Post post,
							  @PathVariable Comment comment,
							  @RequestParam("commentPic") MultipartFile commentPic,
							  @RequestParam String commentText,
							  Model model) throws IllegalStateException, IOException {
		commentService.editComment(currentUser, comment, commentText, commentPic);
		return "redirect:/" + post.getId() + "/comments";
	}

}
