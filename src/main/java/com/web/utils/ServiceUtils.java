package com.web.utils;

import java.util.ArrayList;
import java.util.List;

import com.web.data.Comment;
import com.web.data.FriendRequest;
import com.web.data.Group;
import com.web.data.Post;
import com.web.data.User;

public class ServiceUtils {

	public static List<Comment> findBannedComments(Group group, User bannedUser) {
		List<Comment> result = new ArrayList<Comment>();
		for (Post post : group.getGroupPosts()) {
			for (Comment comment : post.getPostComments()) {
				if (comment.getCommentAuthor().equals(bannedUser)) {
					result.add(comment);
				}
			}
		}
		return result;
	}

	public static List<Comment> findBannedComments(User currentUser, User bannedUser) {
		List<Comment> result = new ArrayList<Comment>();
		for (Post post : currentUser.getUserPosts()) {
			for (Comment comment : post.getPostComments()) {
				if (comment.getCommentAuthor().equals(bannedUser)) {
					result.add(comment);
				}
			}
		}
		return result;
	}
}
