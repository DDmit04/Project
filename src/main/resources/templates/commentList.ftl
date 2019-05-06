<#import "parts/HTMLshell.ftl" as shell>
<#import "parts/showLists/showPost.ftl" as showPost>
 <@shell.htmlPage> 

<#include "parts/security.ftl">

<div class="col-8">
	<#if post??>
		<@showPost.show post 0 "post" />
	</#if>
		<#list comments as comment>
			<#include "parts/showLists/showComment.ftl">
		<#else>
			<h2 class="display-4 mt-3" align="center">No comments...</h2>
		</#list>
	<div class="mt-3">
		<#if post??>
			<#if (post.postGroup?? && !post.postGroup.groupBanList?seq_contains(currentUser)) 
			  || (post.postAuthor?? && !post.postAuthor.blackList?seq_contains(currentUser))>
				<#include "parts/forms/addCommentForm.ftl">
			</#if>
		</#if>
	</div>
</div>

</@shell.htmlPage>