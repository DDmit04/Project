<#macro show post>

<#include "/parts/security.ftl">

<div class="card">
	<div class="card-body border-secondary">
		<#if image.ingUser??>
			<#assign picPath = "/imgUserPic/${image.imgUser.username}/${image.imgFileName}">
		<#elseif image.imgGroup??>
			<#assign picPath = "/imgGroupPic/${image.imgGroup.groupName}/${image.imgFileName}">
		<#elseif image.imgChat??>
			<#assign picPath = "/imgChatPic/${image.imgChat.chatName}/${image.imgFileName}">
		</#if>
      		<img src="${picPath}" width="89" class="card-img-top">
	</div>
    <div class="card-footer">
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

</#macro>