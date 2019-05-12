<#import "/parts/forms/addRepostForm.ftl" as repost>

<#include "/parts/security.ftl">

<a class="mr-3" href="/posts/${post.id}/${currentUser.id}/like">
	<#if post.liked>
		<i class="fas fa-heart"></i>
	<#else>
		<i class="far fa-heart"></i>
	</#if>
	${post.likes}
</a>
<a href="/${post.id}/comments">
	<i class="far fa-comment mr-1"></i>${post.commentsCount}
</a>
<#if !isEdit && (!post.postAuthor?? || currentUsername != post.postAuthor.username)>
	<a class="ml-3" data-toggle="collapse" href="#repost${post.id}" role="button" aria-expanded="false" aria-controls="repost${post.id}"
		onclick="rotateIcon('repostIcon', ${post.id});">
   		<i id="repostIcon${post.id}" class="fas fa-sign-in-alt mr-1 "></i>${post.repostsCount}
 	</a>
 	<div class="collapse mt-3" id="repost${post.id}">
 		<@repost.postRepost adminedGroups post />
 	</div>
</#if>
