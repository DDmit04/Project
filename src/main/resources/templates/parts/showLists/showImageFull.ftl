<#import "/parts/forms/addImageRepostForm.ftl" as imageRepost>

<#include "/parts/security.ftl">

<#if image.imgUser??>
	<#assign picPath = "/imgUserPic/${image.imgUser.username}">
<#elseif image.imgGroup??>
	<#assign picPath = "/imgGroupPic/${image.imgGroup.groupName}">
<#elseif image.imgChat??>
	<#assign picPath = "/imgChatPic/${image.imgChat.chatName}">
</#if>

<div class="card border-secondary shadow">
	<div class="card-body">
      	<img src="${picPath}/${image.imgFileName}" width="89" class="card-img-top">
	</div>
    <div class="card-footer border-secondary">
        <#include "/parts/components/image/imageFooter.ftl">
		<div class="collapse mt-3" id="repost${image.id}">
 		 	<@imageRepost.imageRepost adminedGroups image />
		</div>
    </div>
</div>

<script src="/customJs/animations.js"></script>
