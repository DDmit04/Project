package com.web.data;

import java.util.HashSet;
import java.util.Objects;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "grup")
public class Group {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String groupName;
	private String groupInformation;
	private String creationDate;
	private String groupPicName;
	private String groupTitle;
	
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
	private User groupOwner;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) 
	@JoinTable(name = "group_subs", 
				joinColumns = { @JoinColumn(name = "group_id") },
				inverseJoinColumns = { @JoinColumn(name = "user_id") } 
	)
	private Set<User> groupSubs = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   	@JoinTable(name = "banned_users", 
   		joinColumns = { @JoinColumn(name = "group_id") },
   		inverseJoinColumns = { @JoinColumn(name = "user_id") } 
   	)
	private Set<User> banList = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "admined_groups", 
		joinColumns = { @JoinColumn(name = "group_id") },
		inverseJoinColumns = { @JoinColumn(name = "user_id") } 
	)
	private Set<User> groupAdmins = new HashSet<>();
	
	@OneToMany(mappedBy = "postGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Post> groupPosts;

	public Group(String groupName, String groupInformation, String groupTitle,  String creationDate) {
		this.creationDate = creationDate;
		this.groupName = groupName;
		this.groupInformation = groupInformation;
		this.groupTitle = groupTitle;
	}
}