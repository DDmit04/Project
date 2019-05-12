<#macro show image>

<#import "/parts/components/image/imageModal.ftl" as imageModal>

<#include "/parts/security.ftl">

	<#if image.imgUser??>
		<#assign picPath = "/imgUserPic/${image.imgUser.username}">
	<#elseif image.imgGroup??>
		<#assign picPath = "/imgGroupPic/${image.imgGroup.groupName}">
	<#elseif image.imgChat??>
		<#assign picPath = "/imgChatPic/${image.imgChat.chatName}">
	</#if>
	<a data-toggle="modal" data-target="#imageModal${image.id}">
		<img src="${picPath}/${image.imgFileName}" width="89" class="card-img-top">
	</a>
	<!-- image modal -->
	<div class="modal fade" id="imageModal${image.id}" tabindex="-1" role="dialog" aria-hidden="true">
		<@imageModal.modal image picPath false />
	</div>

<script src="/customJs/animations.js"></script>

</#macro>