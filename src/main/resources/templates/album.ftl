<#import "parts/HTMLshell.ftl" as shell> 
<#import "parts/showLists/showListsOfGroups.ftl" as groupList> 
<#import "/parts/showLists/showListsOfUsers.ftl" as userLists> 
<#import "/parts/forms/addImageRepostForm.ftl" as imageRepost>
<#import "/parts/components/image/imageModal.ftl" as imageModal>

<@shell.htmlPage> 

<#include "parts/security.ftl">

<#if viewedUser??>
	<#assign picPath = "/imgUserPic/${user.username}"
			 backPath = "/${user.id}/profile">
<#elseif currentChat??>
	<#assign picPath = "/imgChatPic/${chat.chatName}"
			 backPath = "/chats/${chat.id}">
<#elseif currentGroup??>
	<#assign picPath = "/imgGroupPic/${group.groupName}"
			 backPath = "/groups/${group.id}">
</#if>

<div class="container mt-3">
	<#if (viewedUser?? && viewedUser.id == currentUser.id)
	 	|| (currentChat?? && currentChat.chatAdmins?seq_contains(currentUser))
	 	|| (currentGroup?? && currentGroup.groupAdmins?seq_contains(currentUser))>
	 	<div class="row mb-4">
			<#include "/parts/forms/addImageForm.ftl">
		</div>
	</#if>
    <div class="row">
        <div class="card-group">
			<#list images as image>
            	<div class="card mb-2 mr-2 shadow" style="min-width: 8rem; max-width: 1rem">
  					<div class="card-body ">
  						<a data-toggle="modal" data-target="#imageModal${image.id}">
							<img src="${picPath}/${image.imgFileName}" width="110" height="110">
						</a>
  					</div>
				</div>
				<!-- image modal -->
				<div class="modal fade" id="imageModal${image.id}" tabindex="-1" role="dialog" aria-hidden="true">
					<@imageModal.modal image picPath true />
				</div>
			<#else>
				<h4 class="display-4 ml-2 " align="left">no images :(</h4>	
			</#list>
		</div>
	</div>
</div>

</@shell.htmlPage>