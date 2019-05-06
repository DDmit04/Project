<#import "parts/HTMLshell.ftl" as shell>
<#import "parts/showLists/showPost.ftl" as showPost>
 <@shell.htmlPage> 

<#include "parts/security.ftl">

<div class="col-8">
	<div class="card border-secondary shadow">
		<div class="card-body">
			<#if image.imgUser??>
				<#assign picPath = "/imgUserPic/${image.imgUser.username}/${image.imgFileName}">
			<#elseif image.imgGroup??>
				<#assign picPath = "/imgGroupPic/${image.imgGroup.groupName}/${image.imgFileName}">
			<#elseif image.imgChat??>
				<#assign picPath = "/imgChatPic/${image.imgChat.chatName}/${image.imgFileName}">
			</#if>
       		<img src="${picPath}" width="89" class="card-img-top">
		</div>
	    <div class="card-footer border-secondary">
	        <a class="mr-3" href="/images/${image.id}/${currentUser.id}/like">
				<#if image.liked>
					<i class="fas fa-heart"></i>
				<#else>
					<i class="far fa-heart"></i>
				</#if>
				${image.likes}
			</a>
			<a href="/images/${image.id}/comments">
				<i class="far fa-comment mr-1"></i>${image.commentsCount}
			</a>
	      </div>
	</div>
	<#list comments as comment>
		<#include "parts/showLists/showComment.ftl">
	<#else>
		<h2 class="display-4 mt-3" align="center">No comments...</h2>
	</#list>
	<div class="mt-3">
		<#if (image.imgGroup?? && !image.imgGroup.groupBanList?seq_contains(currentUser))
		  || (image.imgUser?? && !image.imgUser.blackList?seq_contains(currentUser))
		  || (image.imgChat?? && !image.imgChat.chatBanList?seq_contains(currentUser))>
			<#include "parts/forms/addCommentForm.ftl">
		</#if>
	</div>
</div>

</@shell.htmlPage>