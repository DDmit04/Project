package com.forum;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.web.WebApplication;
import com.web.data.Comment;
import com.web.data.Post;
import com.web.data.User;
import com.web.repository.CommentRepo;
import com.web.repository.ImageRepo;
import com.web.service.CommentServiceImpl;
import com.web.service.FileServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
@TestPropertySource(locations="classpath:application.properties") 
public class CommentServiceTest {
	
	@Autowired
	private CommentServiceImpl commentService;
	
	@MockBean
	private CommentRepo commentRepo;
	
	@MockBean
	private ImageRepo imageRepo;
	
	@MockBean
	private FileServiceImpl fileService;

	@Test
	public void testAddComment() throws IllegalStateException, IOException {
		User user = new User("1", "1", null);
		Post post = new Post("text", "tag", null);
		MockMultipartFile file = new MockMultipartFile("file", "Hello, World!".getBytes());
		Comment comment = new Comment("text", null);
		commentService.createComment(user, comment, post, file);
		assertNotNull(comment.getCommentCreationDate());
		assertEquals(comment.getCommentedPost(), post);
		assertEquals(comment.getCommentAuthor(), user);
		Mockito.verify(commentRepo, Mockito.times(2)).save(Mockito.any());
	}

	@Test
	public void testEditComment() throws IllegalStateException, IOException {
		User user = new User("1", "1", null);
		Comment comment = new Comment("text", null);
		comment.setCommentAuthor(user);
		MockMultipartFile file = new MockMultipartFile("file", "Hello, World!".getBytes());
		commentService.editComment(user, comment, "testText", file);
		assertEquals(comment.getCommentText(), "testText");
		assertNotNull(comment.getCommentCreationDate());
		Mockito.verify(commentRepo, Mockito.times(2)).save(Mockito.any());
	}
	
	@Test
	public void testEditCommentFail() throws IllegalStateException, IOException {
		User user = new User("1", "1", null);
		User difUser = new User("2", "1", null);
		Comment comment = new Comment("text", null);
		comment.setCommentAuthor(difUser);
		commentService.editComment(user, comment, "testText", null);
		assertNotEquals(comment.getCommentText(), "testText");
		assertNull(comment.getCommentCreationDate());
		Mockito.verify(commentRepo, Mockito.times(0)).save(Mockito.any());
	}
	
	@Test
	public void testDeleteComment() throws IllegalStateException, IOException {
		User user = new User("1", "1", null);
		Comment comment = new Comment("text", null);
		Post post = new Post("text", "tags", null);
		comment.setCommentAuthor(user);
		commentService.deleteComment(user, post, comment);
		Mockito.verify(commentRepo, Mockito.times(1)).delete(Mockito.any());
	}
	
	@Test
	public void testDeleteCommentFail() throws IllegalStateException, IOException {
		User user = new User("1", "1", null);
		User difUser = new User("2", "1", null);
		Comment comment = new Comment("text", null);
		Post post = new Post("text", "tags", null);
		comment.setCommentAuthor(difUser);
		commentService.deleteComment(user, post, comment);
		Mockito.verify(commentRepo, Mockito.times(0)).delete(Mockito.any());
	}

}
