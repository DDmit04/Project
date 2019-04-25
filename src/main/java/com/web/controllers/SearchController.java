package com.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.web.api.SearchService;
import com.web.data.User;
import com.web.data.dto.PostDto;
import com.web.service.SearchServiceImpl;

@Controller
public class SearchController {
	
	private SearchService searchService;
	
	@Autowired
	public SearchController(SearchServiceImpl searchService) {
		this.searchService = searchService;
	}

	@GetMapping("/search")
	public String search(@AuthenticationPrincipal User currentUser,
						 @RequestParam String search,
						 @RequestParam String searchType,
						 Model model) {
		Iterable<?> searchResults = searchService.search(currentUser, search, searchType);
		model.addAttribute("user", currentUser);
		model.addAttribute("searchResults", searchResults);
		model.addAttribute("search", search);
		model.addAttribute("searchType", searchType);
		return "searchList";
	}
	
	@GetMapping("/subscriptionPosts")
	public String getSubscriptionPosts(@AuthenticationPrincipal User currentUser,
									   @RequestParam(required = false) String search,
									   Model model) {
		Iterable<PostDto> searchFriendPosts = searchService.findSubscriptionsPosts(currentUser);
		model.addAttribute("user", currentUser);
		model.addAttribute("posts", searchFriendPosts);
		model.addAttribute("search", search);
		return "postList";
	}
	
}
