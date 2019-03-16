package com.web.data;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
public class UserGroup {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String groupName;
	private String groupInformation;
	private String creationDate;
//	private String groupPicName;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User groupOwner;
	
	@ManyToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL) 
	@JoinTable(name = "group_subs", 
				joinColumns = { @JoinColumn(name = "user_group_id") },
				inverseJoinColumns = { @JoinColumn(name = "user_id") } 
	)
	private Set<User> groupSubs = new HashSet<>();
	
//	@ManyToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
//   	@JoinTable(name = "banned_users", 
//   		joinColumns = { @JoinColumn(name = "group_id") },
//   		inverseJoinColumns = { @JoinColumn(name = "user_id") } 
//   	)
//	private Set<User> banList = new HashSet<>();
	
//	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "adminedGroups", cascade = CascadeType.ALL)
//	private Set<User> groupAdmins = new HashSet<>();
//	
	@OneToMany(mappedBy = "postGroup", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<Post> groupPosts = new HashSet<>();

	public UserGroup() {
	}
	public UserGroup(String groupName, String groupInformation, String creationDate) {
		this.creationDate = creationDate;
		this.groupName = groupName;
		this.groupInformation = groupInformation;
	}
	public Set<User> getGroupSubs() {
		return groupSubs;
	}
	public void setGroupSubs(Set<User> groupSubs) {
		this.groupSubs = groupSubs;
	}
//	public Set<User> getBanList() {
//		return banList;
//	}
//	public void setBanList(Set<User> banList) {
//		this.banList = banList;
//	}
//	public Set<User> getGroupAdmins() {
//		return groupAdmins;
//	}
//	public void setGroupAdmins(Set<User> groupAdmins) {
//		this.groupAdmins = groupAdmins;
//	}
	public User getGroupOwner() {
		return groupOwner;
	}
	public void setGroupOwner(User groupOwner) {
		this.groupOwner = groupOwner;
	}
	public Set<Post> getGroupPosts() {
		return groupPosts;
	}
	public void setGroupPosts(Set<Post> groupPosts) {
		this.groupPosts = groupPosts;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupInformation() {
		return groupInformation;
	}
	public void setGroupInformation(String groupInformation) {
		this.groupInformation = groupInformation;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
//	public Set<User> getGroupSubs() {
//		return groupSubs;
//	}
//	public void setGroupSubs(Set<User> groupSubs) {
//		this.groupSubs = groupSubs;
//	}
//	public Set<User> getBanList() {
//		return banList;
//	}
//	public void setBanList(Set<User> banList) {
//		this.banList = banList;
//	}
//	public Set<User> getGroupAdmins() {
//		return groupAdmins;
//	}
//	public void setGroupAdmins(Set<User> groupAdmins) {
//		this.groupAdmins = groupAdmins;
//	}
//	public Set<Post> getGroupPosts() {
//		return groupPosts;
//	}
//	public void setGroupPosts(Set<Post> groupPosts) {
//		this.groupPosts = groupPosts;
//	}
//	public String getGroupPicName() {
//		return groupPicName;
//	}
//	public void setGroupPicName(String groupPicName) {
//		this.groupPicName = groupPicName;
//	}
}
