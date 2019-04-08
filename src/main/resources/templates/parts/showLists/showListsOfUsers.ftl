<#macro showUsers usersList listType>

<#if listType == "groupOwner">
	<#assign user = usersList >
	<#include "/parts/showLists/showUser.ftl" >
<#else>
	<#list usersList as user>
		<#include "/parts/showLists/showUser.ftl" >
	<#else>
		<#if listType == "friends">
			<h4 class="display-4 ml-2 " align="left">No friends :(</h4>			
		<#elseif listType == "subscriptions">
			<h4 class="display-4 ml-2 " align="left">No subscriptions :(</h4>
		<#elseif listType == "groupSubscrabers" || listType == "subscribers">
			<h4 class="display-4 ml-2 " align="left">No subscribers :(</h4>
		<#elseif listType == "groupAdmins">
			<h4 class="display-4 ml-2 " align="left">No admins :(</h4>
		<#elseif listType == "groupBanList">
			<h4 class="display-4 ml-2 " align="left">No bans :(</h4>
		<#elseif listType == "userBlackList">
			<h4 class="display-4 ml-2 " align="left">black list is empty</h4>
		<#elseif listType == "chatMembers">
			<h4 class="display-4 ml-2 " align="left">empty here</h4>
		<#else>
			<h4 class="display-4 ml-2 " align="left">Empty List</h4>
		</#if>
	</#list>
</#if>
		
</#macro>