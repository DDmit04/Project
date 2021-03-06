<#import "parts/HTMLshell.ftl" as shell> 
<#import "parts/showLists/showListsOfGroups.ftl" as groupList> 
<#import "/parts/showLists/showListsOfUsers.ftl" as userLists> 

<@shell.htmlPage> 

<#include "parts/security.ftl">

<div class="col-md-9">
	<#if searchType == "posts">
		<#assign posts = searchResults> 
		<#include "/parts/showLists/showListsOfPosts.ftl">
	<#elseif searchType == "users">
		<@userLists.showUsers searchResults "searchUsers" />
	<#elseif searchType == "groups">
		<@groupList.groupList searchResults />
	</#if>
</div>

</@shell.htmlPage>