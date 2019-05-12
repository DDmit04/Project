package com.web.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.web.api.ImageRepostService;
import com.web.api.ImageService;
import com.web.data.Image;
import com.web.data.Post;
import com.web.data.User;
import com.web.service.ImageRepostServiceImpl;
import com.web.service.ImageServiceImpl;

@Controller
public class ImageController {
	
	private ImageRepostService imageRepostService;
	private ImageService imageService;
	
	@Autowired
	public ImageController(ImageRepostServiceImpl postService, ImageServiceImpl imageService) {
		this.imageRepostService = postService;
		this.imageService = imageService;
	}
	
	@GetMapping("images/{post}/removeRepostImage")
	public String removeRepost(@AuthenticationPrincipal User currentUser,
							   @PathVariable Post post,
			 				   @RequestHeader(required = false) String referer) {
		imageRepostService.removeRepostImage(currentUser, post);
		return "redirect:/" + post.getId() + "/edit" ;		
	}

	@PostMapping("images/{repostedImage}/repost")
	public String addRepostImage(@AuthenticationPrincipal User currentUser,
							@PathVariable Image repostedImage, 
			 				@RequestParam(required = false) String repostText,
			 				@RequestParam(required = false) String repostTags,
			 				@RequestParam(defaultValue = "false") boolean groupRepostCheckbox,
			 				@RequestParam(required = false) Long groupRepostSelect) throws IllegalStateException, IOException {
		if(groupRepostCheckbox) {
			imageRepostService.addGroupRepostImage(groupRepostSelect, repostedImage, repostText, repostTags);
		} else {
			imageRepostService.addRepostImage(currentUser, repostedImage, repostText, repostTags);
		}
		return "redirect:/posts/all";
	}
	
	@GetMapping("images/{image}/delete")
	public String deleteImage(@AuthenticationPrincipal User currentUser,
							  @PathVariable Image image,
							  @RequestHeader(required = false) String referer,
							  RedirectAttributes redirectAttributes) {
		imageService.deleteImage(currentUser, image);
		UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();
		components.getQueryParams()
			.entrySet()
			.forEach(pair -> redirectAttributes.addAttribute(pair.getKey(), pair.getValue()));
		return "redirect:" + components.getPath();	
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
