<#import "parts/HTMLshell.ftl" as shell>
<#import "parts/showLists/showGroup.ftl" as groupList> 
 <@shell.htmlPage> 

<#include "parts/security.ftl">

<#if user??>
	<h3>
		<#if currentUsername != user.username>
			${user.username}'s groups (${user.groupSubscriptionsCount})
		<#else>
			My groups (${user.groupSubscriptionsCount})
		</#if>
	</h3>
</#if>

<div class="col-9">
	<div class="card">
		<#list groups as group>
			<@groupList.groupList group 64 64 />	
		<#else>
			<h2 class="display-4 mt-5" align="center">No groups here!</h2>
		</#list>
	</div>
</div>
</@shell.htmlPage>