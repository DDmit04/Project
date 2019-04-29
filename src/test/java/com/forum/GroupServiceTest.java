package com.forum;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.web.WebApplication;
import com.web.data.Group;
import com.web.data.User;
import com.web.exceptions.GroupException;
import com.web.repository.CommentRepo;
import com.web.repository.GroupRepo;
import com.web.service.FileServiceImpl;
import com.web.service.GroupServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
@TestPropertySource(locations="classpath:application.properties") 
public class GroupServiceTest {
	
	@Autowired
	private GroupServiceImpl groupService;
	
	@MockBean
	private GroupRepo groupRepo;
	
	@MockBean
	private FileServiceImpl fileService;
	
	@MockBean
	private CommentRepo commentRepo;
	
	@MockBean
	private PasswordEncoder passwordEncoder; 

	@Test
	public void testCreateGroup() throws IllegalStateException, IOException, GroupException {
		User user = new User("1", "1", null);
		Group group = new Group("name", "inf", "title", null);
		doReturn(null).when(groupRepo).findByGroupName(Mockito.any());
		doReturn("testFilename").when(fileService).uploadFile(Mockito.any(), Mockito.any());
		Group testGroup = groupService.createGroup(group, null, user);
		
		assertTrue(testGroup.getGroupName().equals("name"));
		assertTrue(testGroup.getGroupInformation().equals("inf"));
		assertTrue(testGroup.getGroupTitle().equals("title"));
		assertTrue(testGroup.getGroupOwner().equals(user));
		assertTrue(testGroup.getGroupPicName().equals("testFilename"));
		assertTrue(testGroup.getGroupSubs().contains(user));
		assertTrue(testGroup.getGroupAdmins().contains(user));
		assertNotNull(testGroup.getGroupCreationDate());
		Mockito.verify(groupRepo, Mockito.times(2)).save(Mockito.any());
	}

	@Test
	public void testMakeOwner() {
		User groupOwner = new User("1", "1", null);
		User newGroupOwner = new User("1", "1", null);
		Group group = new Group("name", "inf", "title", null);
		group.setGroupOwner(groupOwner);
		doReturn(true).when(passwordEncoder).matches(Mockito.any(), Mockito.any());
		groupService.makeOwner(groupOwner, newGroupOwner, group, groupOwner.getUsername(), groupOwner.getPassword());
		
		assertTrue(group.getGroupOwner().equals(newGroupOwner));
		Mockito.verify(groupRepo, Mockito.times(1)).save(Mockito.any());
	}

	@Test
	public void testBanUser() {
		User groupAdmin = new User("1", "1", null);
		User bannedUser = new User("1", "1", null);
		Group group = new Group("name", "inf", "title", null);
		group.getGroupAdmins().add(bannedUser);
		group.getGroupAdmins().add(groupAdmin);
		group.setGroupOwner(groupAdmin);
		groupService.banUser(groupAdmin, bannedUser, group);
		
		assertTrue(group.getGroupBanList().contains(bannedUser));
		assertFalse(group.getGroupAdmins().contains(bannedUser));
		Mockito.verify(groupRepo, Mockito.times(1)).save(Mockito.any());
		Mockito.verify(commentRepo, Mockito.times(1)).deleteAll(Mockito.any());
		Mockito.verify(commentRepo, Mockito.times(1)).findBannedComments(group, bannedUser);
	}

	@Test
	public void testUnbanUser() {
		User groupAdmin = new User("1", "1", null);
		User bannedUser = new User("1", "1", null);
		Group group = new Group("name", "inf", "title", null);
		group.getGroupAdmins().add(groupAdmin);
		group.setGroupOwner(groupAdmin);
		group.getGroupBanList().add(bannedUser);
		groupService.unbanUser(groupAdmin, bannedUser, group);
		
		assertFalse(group.getGroupBanList().contains(bannedUser));
		Mockito.verify(groupRepo, Mockito.times(1)).save(Mockito.any());
	}

	@Test
	public void testAddGroupSub() {
		User user = new User("1", "1", null);
		Group group = new Group("name", "inf", "title", null);
		groupService.addGroupSub(user, group);
		assertTrue(group.getGroupSubs().contains(user));
		Mockito.verify(groupRepo, Mockito.times(1)).save(Mockito.any());
	}

	@Test
	public void testRemoveGroupSub() {
		User user = new User("1", "1", null);
		Group group = new Group("name", "inf", "title", null);
		group.getGroupSubs().add(user);
		
		groupService.removeGroupSub(user, group);
		
		assertFalse(group.getGroupSubs().contains(user));
		Mockito.verify(groupRepo, Mockito.times(1)).save(Mockito.any());
	}

	@Test
	public void testAddGroupAdmin() {
		User groupOwner = new User("1", "1", null);
		User user = new User("1", "1", null);
		Group group = new Group("name", "inf", "title", null);
		group.setGroupOwner(groupOwner);
		groupService.addGroupAdmin(user, user, group);
		assertTrue(group.getGroupAdmins().contains(user));
		Mockito.verify(groupRepo, Mockito.times(1)).save(Mockito.any());
	}

	@Test
	public void testRemoveGroupAdmin() {
		User groupOwner = new User("1", "1", null);
		User user = new User("1", "1", null);
		Group group = new Group("name", "inf", "title", null);
		group.setGroupOwner(groupOwner);
		group.getGroupAdmins().add(user);
		groupService.removeGroupAdmin(user, user, group);
		assertFalse(group.getGroupAdmins().contains(user));
		Mockito.verify(groupRepo, Mockito.times(1)).save(Mockito.any());
	}
}
