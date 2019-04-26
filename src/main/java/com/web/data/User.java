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
import javax.persistence.OrderBy;
import javax.persistence.Table;

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
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String username;
	private String password;
	private boolean active;
	private LocalDateTime registrationDate;
	private String userPicName;
	private String userEmail;
	private String emailConfirmCode;
	private String emailChangeCode;
	private String passwordRecoverCode;
	private String userInformation;
	private String userStatus;


	@ElementCollection(targetClass = UserRoles.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
	@Enumerated(EnumType.STRING)
	private Set<UserRoles> roles;

	@OneToMany(mappedBy = "postAuthor", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Post> userPosts;

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
	@Override
	public boolean equals(Object obj) {
    	if(this == obj) 
    		return true;
    	if(obj == null || getClass() != obj.getClass()) 
    		return false;
    	User user = (User) obj;
		return Objects.equals(id, user.id);
	}
	@Override
	public int hashCode() {
		return Objects.hashCode(id);
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
}
