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
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private GroupRepo groupRepo;
	
	@Override
	public Iterable<? extends SearchResultsGeneric> search(User currentUser, String search, String searchType) {
		Iterable<? extends SearchResultsGeneric> searchResult = new HashSet<>();
		if(search != null && search != "") {
			if("users".equals(searchType)) {
				searchResult = userRepo.searchUsersDto(search);
			} else if("posts".equals(searchType)) {
				searchResult = postRepo.findByTag(currentUser, search);
			} else if("groups".equals(searchType)) {
				searchResult = groupRepo.searchGroupsDto(search);
			}
		} else {
			if("users".equals(searchType)) {
				searchResult = userRepo.searchAllUsersDto();
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
