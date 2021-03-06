package com.web.data;

import java.time.LocalDateTime;
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
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "usr")
@Where(clause="deleted = false")
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String username;
	private String password;
	private boolean active;
	private boolean deleted;
	private LocalDateTime registrationDate;
	private String userEmail;
	private String emailConfirmCode;
	private String emailChangeCode;
	private String passwordRecoverCode;
	private String userInformation;
	private String userStatus;
	
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "image_id")
	private Image userImage;

	@ElementCollection(targetClass = UserRoles.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
	@Enumerated(EnumType.STRING)
	private Set<UserRoles> roles;

	@OneToMany(mappedBy = "postAuthor", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Post> userPosts;
	
	@OneToMany(mappedBy = "imgUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Image> userImages;

	@OneToMany(mappedBy = "commentAuthor", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Comment> userComments;
	
	@OneToMany(mappedBy = "groupOwner", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Group> belongGroup;
	
	@OneToMany(mappedBy = "chatOwner", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Chat> belongChat;
	
	@OneToMany(mappedBy = "connectedUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<ChatSession> chatSessions;
	    
	@OrderBy("id")
	@OneToMany(mappedBy = "messageAuthor", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Message> sendedMessages;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "groupSubs")
	private Set<Group> subedGroups;
	
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "groupAdmins")
   	private Set<Group> adminedGroups;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "groupBanList")
	private Set<Group> bannedInGroups;
    
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "chatMembers")
	private Set<Chat> chats;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "chatAdmins")
	private Set<Chat> adminedChats;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "chatBanList")
	private Set<Chat> bannedInChats;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_friendship", 
	    joinColumns = @JoinColumn(name = "first_user_id"), 
	    inverseJoinColumns = @JoinColumn(name = "second_user_id")
	)
	private Set<User> userFriends = new HashSet<User>();
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_subscriptions",
            joinColumns = { @JoinColumn(name = "channel_id") },
            inverseJoinColumns = { @JoinColumn(name = "subscriber_id") }
    )
    private Set<User> subscribers = new HashSet<User>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_subscriptions",
            joinColumns = { @JoinColumn(name = "subscriber_id") },
            inverseJoinColumns = { @JoinColumn(name = "channel_id") }
    )
    private Set<User> subscriptions = new HashSet<User>();
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_blackList",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "bannedUser_id") }
    )
    private Set<User> blackList = new HashSet<User>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_blackList",
            joinColumns = { @JoinColumn(name = "bannedUser_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private Set<User> inBlackList = new HashSet<>();
    
	public User(String username, String password, LocalDateTime registrationDate) {
		this.username = username;
		this.password = password;
		this.registrationDate = registrationDate;
	}
	public String getUsername() {
		return username;
	}	
	public String getPassword() {
		return password;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
}
