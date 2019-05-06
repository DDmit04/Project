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

import com.web.api.CommentService;
import com.web.api.GroupService;
import com.web.api.ImageService;
import com.web.data.Comment;
import com.web.data.Image;
import com.web.data.User;
import com.web.data.dto.CommentDto;
import com.web.data.dto.GroupDto;
import com.web.data.dto.ImageDto;
import com.web.repository.CommentRepo;
import com.web.repository.ImageRepo;
import com.web.service.CommentServiceImpl;
import com.web.service.GroupServiceImpl;

@Controller
public class ImageCommentController {
	
	private CommentService commentService;
	private GroupService groupService;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ImageRepo imageRepo;
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	public ImageCommentController(CommentServiceImpl commentService, GroupServiceImpl groupService) {
		this.commentService = commentService;
		this.groupService = groupService;
	}
	
	@GetMapping("images/{image}/comments")
	public String getImageComments(@AuthenticationPrincipal User currentUser,
							  	   @PathVariable Image image,
							  	   Model model) {
		Iterable<CommentDto> searchByCommentedImage = commentRepo.getCommentsByCommentedImage(image);
		ImageDto commentedImage = imageRepo.getOneImage(currentUser, image.getId());
		Iterable<GroupDto> adminedGroups = groupService.getAdminedGroups(currentUser);
		model.addAttribute("adminedGroups", adminedGroups);
		model.addAttribute("comments", searchByCommentedImage);
		model.addAttribute("image", commentedImage);
		return "imageCommentList";
	}
	
	@PostMapping("images/{image}/comments")
	public String addImageComment(@AuthenticationPrincipal User currentUser,
							 	  @PathVariable Image image,
							 	  @RequestParam("commentPic") MultipartFile commentPic,
							 	  Comment comment,
							 	  Model model) throws IllegalStateException, IOException {
		commentService.createComment(currentUser, comment, image, commentPic);
		return "redirect:/images/" + image.getId() + "/comments";
	}
	
	@GetMapping("images/{image}/comments/{comment}/delete")
	public String deleteImageComment(@AuthenticationPrincipal User currentUser,
							    	 @PathVariable Image image,
							    	 @PathVariable Comment comment,
							    	 Model model) {
		commentService.deleteComment(currentUser, image, comment);
		return "redirect:/images/" + image.getId() + "/comments";
	}
	
	@GetMapping("images/{image}/comments/{comment}/edit")
	public String getEditComment(@AuthenticationPrincipal User currentUser,
								 @PathVariable Image image,
							     @PathVariable Comment comment,
							     Model model) {
		Iterable<CommentDto> searchByCommentedImage = commentRepo.getCommentsByCommentedImage(image);
		ImageDto commentedImage = imageRepo.getOneImage(currentUser, image.getId());
		Iterable<GroupDto> adminedGroups = groupService.getAdminedGroups(currentUser);
		model.addAttribute("adminedGroups", adminedGroups);
		model.addAttribute("comments", searchByCommentedImage);
		model.addAttribute("image", commentedImage);		
		model.addAttribute("editedComment", comment);
		return "commentEdit";
	}
	
	@PostMapping("images/{image}/comments/{comment}/edit")
	public String editComment(@AuthenticationPrincipal User currentUser,
							  @PathVariable Image image,
							  @PathVariable Comment comment,
							  @RequestParam("commentPic") MultipartFile commentPic,
							  @RequestParam String commentText,
							  Model model) throws IllegalStateException, IOException {
		commentService.editComment(currentUser, comment, commentText, commentPic);
		return "redirect:/images/" + image.getId() + "/comments";
	}
	
	@GetMapping("images/{image}/{user}/like")
	public String likeImage(@AuthenticationPrincipal User currentUser,
						    @PathVariable User user,
						    @PathVariable Image image,
						    @RequestHeader(required = false) String referer,
						    RedirectAttributes redirectAttributes) {
		imageService.likeImage(currentUser, user, image);
		UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();
		components.getQueryParams()
			.entrySet()
			.forEach(pair -> redirectAttributes.addAttribute(pair.getKey(), pair.getValue()));
		return "redirect:" + components.getPath();	
	}

}
