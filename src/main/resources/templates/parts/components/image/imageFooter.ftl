<#import "/parts/forms/addImageRepostForm.ftl" as imageRepost>

<#include "/parts/security.ftl">

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

<a class="ml-3" data-toggle="collapse" href="#repost${image.id}" role="button" aria-expanded="false" aria-controls="repost${image.id}"
	onclick="rotateIcon('repostIcon', ${image.id});">
	<i id="repostIcon${image.id}" class="fas fa-sign-in-alt mr-1 "></i>
	${image.imageRepostCount}
</a>
<div class="collapse mt-3" id="repost${image.id}">
 	<@imageRepost.imageRepost adminedGroups image />
</div>

<div class="text-right">
	<#if ((image.imgUser?? && image.imgUser == currentUser)
		|| (image.imgGroup?? && image.imgGroup.groupAdmins?seq_contains(currentUser)) 
		|| (image.imgChat?? && image.imgChat.chatAdmins?seq_contains(currentUser)))>
		<a class="text-right" href="/images/${image.id}/delete">delete image</a> 
	</#if>
</div>

<script src="/customJs/animations.js"></script>