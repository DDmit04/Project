package com.web.api;

import com.web.data.User;
import com.web.data.dto.PostDto;
import com.web.data.dto.SearchResultsGeneric;

public interface SearchService {
	
	Iterable<? extends SearchResultsGeneric> search(User user, String search, String searchType);
	
	Iterable<PostDto> findSubscriptionsPosts(User user);

}
