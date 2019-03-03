package com.web.data;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "usr")
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String username;
	private String password;
	private boolean active;
	private String registrationDate;
	private String userPicName;

	@ElementCollection(targetClass = UserRoles.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
	@Enumerated(EnumType.STRING)
	private Set<UserRoles> roles;

	@OneToMany(mappedBy = "postAuthor", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Post> userPosts;

	@OneToMany(mappedBy = "commentAuthor", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Comment> userComments;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_friendship", 
	    joinColumns = @JoinColumn(name = "first_user_id"), 
	    inverseJoinColumns = @JoinColumn(name = "second_user_id")
	)
	private Set<User> userFriends = new HashSet<User>();
	

	public User() {
	}
	public User(String username, String password, String registrationDate) {
		this.username = username;
		this.password = password;
		this.registrationDate = registrationDate;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		User user = (User) obj;
		return Objects.equals(id, user.id);
	}
	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}
	public Set<Comment> getUserComments() {
		return userComments;
	}
	public void setUserComments(Set<Comment> userComments) {
		this.userComments = userComments;
	}
	public Set<Post> getUserPosts() {
		return userPosts;
	}
	public void setUserPosts(Set<Post> userPosts) {
		this.userPosts = userPosts;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Set<UserRoles> getRoles() {
		return roles;
	}
	public void setRoles(Set<UserRoles> set) {
		this.roles = set;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getRoles();
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return isActive();
	}
	public String getUserPicName() {
		return userPicName;
	}
	public void setUserPicName(String userPicName) {
		this.userPicName = userPicName;
	}
	public Set<User> getUserFriends() {
		return userFriends;
	}
	public void setUserFriends(Set<User> userFrends) {
		this.userFriends = userFrends;
	}
}
