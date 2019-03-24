<#import "parts/HTMLshell.ftl" as shell> <@shell.htmlPage> 

<#include "parts/security.ftl">

<div class="col-9">
	<h3>
		<#if currentUsername != user.username>
			${user.username}'s
		<#else>
			Your
		</#if> 
		friendlist: (${user.friendCount})
	</h3>
	<#list friends as friend>
		<#include "parts/showLists/showFriend.ftl">
	<#else>
		<h2 class="display-4 mt-3" align="left">No frends :(</h2>
	</#list>
</div>

</@shell.htmlPage> 
