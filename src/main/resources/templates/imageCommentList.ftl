<#import "parts/HTMLshell.ftl" as shell>
<#import "parts/showLists/showPost.ftl" as showPost>
 <@shell.htmlPage> 

<#include "parts/security.ftl">

<div class="col-8">
	<#include "/parts/showLists/showImageFull.ftl">
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

<script src="/customJs/animations.js"></script>


</@shell.htmlPage>