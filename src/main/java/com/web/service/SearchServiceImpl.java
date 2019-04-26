package com.web.service;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.api.SearchService;
import com.web.data.User;
import com.web.data.dto.PostDto;
import com.web.data.dto.SearchResultsGeneric;
import com.web.repository.GroupRepo;
import com.web.repository.PostRepo;
import com.web.repository.UserRepo;

@Service
public class SearchServiceImpl implements SearchService {
	
	private PostRepo postRepo;
	private UserRepo userRepo;
	private GroupRepo groupRepo;
	
	@Autowired
	public SearchServiceImpl(PostRepo postRepo, UserRepo userRepo, GroupRepo groupRepo) {
		this.postRepo = postRepo;
		this.userRepo = userRepo;
		this.groupRepo = groupRepo;
	}

	@Override
	public Iterable<? extends SearchResultsGeneric> search(User currentUser, String search, String searchType) {
		Iterable<? extends SearchResultsGeneric> searchResult = new HashSet<>();
		if(search != null && search != "") {
			if("users".equals(searchType)) {
				searchResult = userRepo.searchUsers(search);
			} else if("posts".equals(searchType)) {
				searchResult = postRepo.findByTag(currentUser, search);
			} else if("groups".equals(searchType)) {
				searchResult = groupRepo.searchGroupsDto(search);
			}
		} else {
			if("users".equals(searchType)) {
				searchResult = userRepo.searchAllUsers();
			} else if("posts".equals(searchType)) {
				searchResult = postRepo.findAll(currentUser);
			} else if("groups".equals(searchType)) {
				searchResult = groupRepo.findAllGroupsDto();
			}
		}		
		return searchResult;
	}
	
	@Override
	public Iterable<PostDto> findSubscriptionsPosts(User currentUser) {
		return postRepo.findSubscriptionsPosts(currentUser);
	}

}
