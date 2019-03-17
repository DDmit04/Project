<#import "parts/HTMLshell.ftl" as shell> <@shell.htmlPage> 

<#include "parts/security.ftl">

<#if user??>
	<h3>
		<#if currentUsername != user.username>
			${user.username}'s groups
		<#else>
			My groups
		</#if>
	</h3>
</#if>

<div class="col-8">
	<#list groups as group>
		<#include "parts/showLists/showGroup.ftl">
	<#else>
		<h2 class="display-4 mt-5" align="center">No groups here!</h2>
	</#list>
</div>
</@shell.htmlPage>