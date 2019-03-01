package com.web.data;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "appGroups")
public class Group {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String groupName;
	private String groupInformation;
	private String creationDate;
//	private Set<User> groupSubs;
//	private Set<User> banList;
//	private Set<User> groupAdmins;
//	private Set<Post> groupPosts;

	public Group() {
		
	}
	public Group(String groupName, String creationDate) {
		this.creationDate = creationDate;
		this.groupName = groupName;
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
}
