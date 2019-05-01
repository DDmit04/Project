package com.forum;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;

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
import com.web.data.Group;
import com.web.data.Post;
import com.web.data.User;
import com.web.repository.PostRepo;
import com.web.service.FileServiceImpl;
import com.web.service.PostServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
@TestPropertySource(locations="classpath:application.properties") 
public class PostServiceTest {
	
	@MockBean
	private FileServiceImpl fileService;
	
	@MockBean
	private PostRepo postRepo;
	
	@Autowired
	private PostServiceImpl postService;

	@Test
	public void testCreatePostUserPostMultipartFile() throws IllegalStateException, IOException {
		Post post = new Post("text", "tag", null);
		User user = new User("1", "1", null);
		MockMultipartFile file = new MockMultipartFile("file", "Hello, World!".getBytes());
		doReturn("testFilename").when(fileService).uploadFile(Mockito.any(), Mockito.any());
		Post testPost = postService.createPost(user, post, file);
		assertTrue(testPost.getPostAuthor().equals(user));
		assertTrue(testPost.getFilename().contentEquals("testFilename"));
		assertNotNull(testPost.getPostCreationDate());
		Mockito.verify(postRepo, Mockito.times(1)).save(post);
	}

	@Test
	public void testCreatePostGroupPostMultipartFile() throws IllegalStateException, IOException {
		Post post = new Post("text", "tag", null);
		Group group = new Group("name", "info", "title", null);
		MockMultipartFile file = new MockMultipartFile("file", "Hello, World!".getBytes());
		doReturn("testFilename").when(fileService).uploadFile(Mockito.any(), Mockito.any());
		Post testPost = postService.createPost(group, post, file);
		assertTrue(testPost.getPostGroup().equals(group));
		assertTrue(testPost.getFilename().contentEquals("testFilename"));
		assertNotNull(testPost.getPostCreationDate());
		Mockito.verify(postRepo, Mockito.times(1)).save(post);
	}

	@Test
	public void testUpdatePost() throws IllegalStateException, IOException {
		Post post = new Post("text", "tag", null);
		User user = new User("1", "1", null);
		post.setPostAuthor(user);
		MockMultipartFile file = new MockMultipartFile("file", "Hello, World!".getBytes());
		doReturn("testFilename").when(fileService).uploadFile(Mockito.any(), Mockito.any());
		postService.updatePost(user, post, "testText", "testTag", file);
		assertEquals(post.getPostText(), "testText");
		assertEquals(post.getTags(), "testTag");
		assertEquals(post.getFilename(), "testFilename");
		assertNotNull(post.getPostCreationDate());
		Mockito.verify(postRepo, Mockito.times(1)).save(post);
	}
	
	@Test
	public void testUpdatePostFail() throws IllegalStateException, IOException {
		Post post = new Post("text", "tag", null);
		User user = new User("1", "1", null);
		MockMultipartFile file = new MockMultipartFile("file", "Hello, World!".getBytes());
		doReturn("testFilename").when(fileService).uploadFile(Mockito.any(), Mockito.any());
		postService.updatePost(user, post, "testText", "testTag", file);
		assertNotEquals(post.getPostText(), "testText");
		assertNotEquals(post.getTags(), "testTag");
		assertNotEquals(post.getFilename(), "testFilename");
		assertNull(post.getPostCreationDate());
		Mockito.verify(postRepo, Mockito.times(0)).save(post);
	}

	@Test
	public void testDeletePost() {
		Post post = new Post("text", "tag", null);
		Post repostedPost = new Post("repText", "repTag", null);
		User user = new User("1", "1", null);
		post.setPostAuthor(user);
		post.setRepost(repostedPost);
		doReturn((long) 1).when(postRepo).findCountByRepostAndAuthor(Mockito.any(), Mockito.any());
		postService.deletePost(user, post);
		Mockito.verify(postRepo, Mockito.times(1)).delete(post);
		Mockito.verify(postRepo, Mockito.times(1)).findCountByRepostAndAuthor(Mockito.any(), Mockito.any());
	}
	
	@Test
	public void testDeletePostFail() {
		Post post = new Post("text", "tag", null);
		Post repostedPost = new Post("repText", "repTag", null);
		User user = new User("1", "1", null);
		post.setRepost(repostedPost);
		doReturn((long) 1).when(postRepo).findCountByRepostAndAuthor(Mockito.any(), Mockito.any());
		postService.deletePost(user, post);
		Mockito.verify(postRepo, Mockito.times(0)).delete(post);
		Mockito.verify(postRepo, Mockito.times(0)).findCountByRepostAndAuthor(Mockito.any(), Mockito.any());
	}

	@Test
	public void testRemoveRepost() {
		Post post = new Post("text", "tag", null);
		Post repostedPost = new Post("repText", "repTag", null);
		User user = new User("1", "1", null);
		post.setPostAuthor(user);
		repostedPost.setRepostsCount((long) 1);
		post.setRepost(repostedPost);
		postService.removeRepost(user, post);
		assertTrue(repostedPost.getRepostsCount() == 0);
		assertNull(post.getRepost());
		//1 for decrement repost count, 1 to save changes
		Mockito.verify(postRepo, Mockito.times(2)).save(Mockito.any());
	}
	
	@Test
	public void testRemoveRepostFail() {
		Post post = new Post("text", "tag", null);
		Post repostedPost = new Post("repText", "repTag", null);
		User user = new User("1", "1", null);
		repostedPost.setRepostsCount((long) 1);
		post.setRepost(repostedPost);
		postService.removeRepost(user, post);
		assertFalse(repostedPost.getRepostsCount() == 0);
		assertNotNull(post.getRepost());
		Mockito.verify(postRepo, Mockito.times(0)).save(Mockito.any());
	}

	@Test
	public void testAddRepost() throws IllegalStateException, IOException {
		Post post = new Post("text", "tag", null);
		User user = new User("1", "1", null);
		post.setPostAuthor(user);
		post.setRepostsCount((long) 1);
		doReturn((long) 0).when(postRepo).findCountByRepostAndAuthor(Mockito.any(), Mockito.any());
		Post testPost = postService.addRepost(user, post, "repText", "repTag");
		assertEquals(testPost.getPostText(), "repText");
		assertEquals(testPost.getTags(), "repTag");
		assertNull(testPost.getFilename());
		assertNotNull(testPost.getRepost());
		assertTrue(post.getRepostsCount() == 2);
		//1 for increment repost count, 1 to create post, 1 to create repost
		Mockito.verify(postRepo, Mockito.times(3)).save(Mockito.any());
	}

	@Test
	public void testLike() {
		User user = new User("1", "1", null);
		Post post = new Post("text", "tag", null);
		postService.like(user, user, post);
		assertTrue(post.getPostLikes().contains(user));
	}
	
	@Test
	public void testLikeFail() {
		User user = new User("1", "1", null);
		User difUser = new User("2", "1", null);
		Post post = new Post("text", "tag", null);
		postService.like(user, difUser, post);
		assertFalse(post.getPostLikes().contains(user));
	}
	
	@Test
	public void testDisike() {
		User user = new User("1", "1", null);
		Post post = new Post("text", "tag", null);
		post.getPostLikes().add(user);
		postService.like(user, user, post);
		assertFalse(post.getPostLikes().contains(user));
	}
	
	@Test
	public void testDisikeFail() {
		User user = new User("1", "1", null);
		User difUser = new User("2", "1", null);
		Post post = new Post("text", "tag", null);
		post.getPostLikes().add(user);
		postService.like(user, difUser, post);
		assertTrue(post.getPostLikes().contains(user));
	}

}
