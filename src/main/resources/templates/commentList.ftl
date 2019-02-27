<#import "parts/HTMLshell.ftl" as shell> <@shell.htmlPage> 

<#include "parts/security.ftl">

<div class="col-8">
	<#include "parts/posts/showPost.ftl">
	<div class="col-8 mt-3">
		<#list comments as comment>
			<ul class="list-group list-group-flush shadow border border-secondary mt-1">
				  <li class="list-group-item">
					  <div class="border-secondary media">
							<a href="/${comment.commentAuthor.username}/profile">
								<#if comment.commentAuthor.userPicName??>
									<img class="mr-2 rounded-circle border border-secondary" src="/imgUserPic/${comment.commentAuthor.userPicName}" width="34" height="34" class="mr-3">
								<#else>
									<img class="mr-2 rounded-circle border border-secondary" src="http://localhost:8080/static/images/title1.png" width="34" height="34" class="mr-3">
								</#if>
							</a>
					  <div class="media-body">
					  	  <div>
						  	  <a href="/${post.postAuthor.username}/profile" class="h6 ml-2">${comment.commentAuthor.username}</a>
						  </div>
						  <div class="ml-2">
							  <div>${comment.commentText}</div>
							  <small>${comment.creationDate}</small>
						  </div>
					  </div>
				  </li>
			</ul>
		<#else>
			<h2 class="display-4 mt-3" align="center">No comments...</h2>
		</#list>
		<div class="mt-3">
			<#include "parts/forms/addCommentForm.ftl">
		</div>
	</div>
</div>
</@shell.htmlPage>