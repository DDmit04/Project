package com.forum;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import com.web.data.FriendRequest;
import com.web.data.Image;
import com.web.data.User;
import com.web.repository.CommentRepo;
import com.web.repository.FriendRequestRepo;
import com.web.repository.UserRepo;
import com.web.service.FileServiceImpl;
import com.web.service.ImageServiceImpl;
import com.web.service.UserProfileServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
@TestPropertySource(locations="classpath:application.properties") 
public class UserProfileServiceTest {
	
	@MockBean
	private FriendRequestRepo friendRequestRepo;
	
	@MockBean
	private UserRepo userRepo;
	
	@MockBean
	private CommentRepo commentRepo;
	
	@MockBean
	private FileServiceImpl fileService;
	
	@Autowired
	private UserProfileServiceImpl userProfileService;

	@Test
	public void testAddFriend() {
		User userRequestFrom = new User("1", "1", null);
		User userRequestTo = new User("2", "2", null);
		FriendRequest frendRequest = new FriendRequest(null, userRequestFrom, userRequestTo);
		userProfileService.addFriend(frendRequest);
		Mockito.verify(userRepo, Mockito.times(2)).save(Mockito.any());
		assertTrue(userRequestFrom.getUserFriends().contains(userRequestTo));
		assertTrue(userRequestTo.getUserFriends().contains(userRequestFrom));
	}

	@Test
	public void testDeleteFriend() {
		User user = new User("1", "1", null);
		User userFriend = new User("2", "2", null);
		user.getUserFriends().add(userFriend);
		userProfileService.deleteFriend(user, userFriend);
		Mockito.verify(userRepo, Mockito.times(1)).save(Mockito.any());
		assertFalse(user.getUserFriends().contains(userFriend));
	}
	
	@Test
	public void testRemoveSubscription() {
		User user = new User("1", "1", null);
		User userSub = new User("2", "2", null);
		user.getSubscribers().add(userSub);
		userProfileService.removeSubscription(user, userSub);
		Mockito.verify(userRepo, Mockito.times(1)).save(user);
		assertFalse(user.getSubscribers().contains(userSub));
		assertFalse(userSub.getSubscriptions().contains(user));
	}

	@Test
	public void testAddSubscription() {
		User user = new User("1", "1", null);
		User userSub = new User("2", "2", null);
		userProfileService.addSubscription(user, userSub);
		Mockito.verify(userRepo, Mockito.times(1)).save(user);
		assertTrue(user.getSubscribers().contains(userSub));
	}

	@Test
	public void testAddInBlackList() {
		User user = new User("1", "1", null);
		User bannedUser = new User("2", "2", null);
		user.getSubscribers().add(bannedUser);
		bannedUser.getSubscribers().add(user);
		user.getUserFriends().add(bannedUser);
		bannedUser.getUserFriends().add(user);
		userProfileService.addInBlackList(bannedUser, user);
		//1 to remove sub, 1 to remove friends(many to many)
		Mockito.verify(userRepo, Mockito.times(2)).save(Mockito.any());
		//find both counter requests
		Mockito.verify(friendRequestRepo, Mockito.times(2)).findOneRequest(Mockito.any(), Mockito.any());
		//delete both counter requests
		Mockito.verify(friendRequestRepo, Mockito.times(2)).delete(friendRequestRepo.findOneRequest(Mockito.any(), Mockito.any()));
		Mockito.verify(commentRepo, Mockito.times(1)).findBannedComments(user, bannedUser);
		Mockito.verify(commentRepo, Mockito.times(1)).deleteAll(commentRepo.findBannedComments(user, bannedUser));
		assertFalse(user.getSubscriptions().contains(bannedUser));
		assertFalse(bannedUser.getSubscriptions().contains(user));
		assertFalse(bannedUser.getUserFriends().contains(user));
		assertTrue(user.getBlackList().contains(bannedUser));
	}

	@Test
	public void testUpdateUserProfile() throws IllegalStateException, IOException {
		User user = new User("1", "1", null);
		user.setUserInformation("inf");
		user.setUserStatus("stat");
		userProfileService.updateUserProfile(user, user, null, "testInf", "testStat");
		Mockito.verify(userRepo, Mockito.times(1)).save(user);
		assertEquals(user.getUserInformation(), "testInf");
		assertEquals(user.getUserStatus(), "testStat");
	}
	
//	@Test
//	public void testUploadUserPic() throws IllegalStateException, IOException {
//		User user = new User("1", "1", null);
//		MockMultipartFile file = new MockMultipartFile("file", "Hello, World!".getBytes());
//		userProfileService.uploadUserPic(user, (MultipartFile) file);
//	}

	@Test
	public void testRemoveFromBlackList() {
		User user = new User("1", "1", null);
		User bannedUser = new User("2", "2", null);
		user.getBlackList().add(bannedUser);
		userProfileService.removeFromBlackList(bannedUser, user);
		Mockito.verify(userRepo, Mockito.times(1)).save(user);
		assertFalse(user.getBlackList().contains(bannedUser));
	}

}
