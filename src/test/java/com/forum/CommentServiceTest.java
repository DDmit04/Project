package com.forum;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.web.WebApplication;
import com.web.data.Comment;
import com.web.data.Post;
import com.web.data.User;
import com.web.repository.CommentRepo;
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
	private FileServiceImpl fileService;

	@Test
	public void testAddComment() throws IllegalStateException, IOException {
		User user = new User("1", "1", null);
		Post post = new Post("text", "tag", null);
		Comment comment = new Comment("text", null);
		doReturn("testFilename").when(fileService).uploadFile(Mockito.any(), Mockito.any());
		commentService.createComment(user, comment, post, null);
		assertNotNull(comment.getCommentCreationDate());
		assertTrue(comment.getCommentedPost().equals(post));
		assertTrue(comment.getCommentPicName().equals("testFilename"));
		assertTrue(comment.getCommentAuthor().equals(user));
		Mockito.verify(commentRepo, Mockito.times(1)).save(Mockito.any());
	}

	@Test
	public void testEditComment() throws IllegalStateException, IOException {
		User user = new User("1", "1", null);
		Comment comment = new Comment("text", null);
		comment.setCommentAuthor(user);
		doReturn("testFilename").when(fileService).uploadFile(Mockito.any(), Mockito.any());
		commentService.editComment(user, comment, "testText", null);
		assertTrue(comment.getCommentText().equals("testText"));
		assertNotNull(comment.getCommentCreationDate());
		assertTrue(comment.getCommentPicName().equals("testFilename"));
		Mockito.verify(commentRepo, Mockito.times(1)).save(Mockito.any());
	}

}
