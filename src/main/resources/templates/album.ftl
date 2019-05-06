<#import "parts/HTMLshell.ftl" as shell> 
<#import "parts/showLists/showListsOfGroups.ftl" as groupList> 
<#import "/parts/showLists/showListsOfUsers.ftl" as userLists> 

<@shell.htmlPage> 

<#include "parts/security.ftl">

<#if viewedUser??>
	<#assign picPath = "/imgUserPic/${user.username}">
<#elseif currentChat??>
	<#assign picPath = "/imgChatPic/${chat.chatName}">
<#elseif currentGroup??>
	<#assign picPath = "/imgGroupPic/${group.groupName}">
</#if>

<div class="container mt-3">
    <div class="row">
        <div class="card-group">
			<#list images as image>
            	<div class="card mb-2 mr-2 shadow" style="min-width: 8rem; max-width: 1rem">
  					<div class="card-body ">
  						<a data-toggle="modal" data-target="#exampleModal${image.id}">
							<img src="${picPath}/${image.imgFileName}" width="110" height="110">
						</a>
  					</div>
				</div>
				<!-- image modal -->
				<div class="modal fade" id="exampleModal${image.id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
				  <div class="modal-dialog" role="document">
				    <div class="modal-content">
				    	<div class="align-right mr-2 mt-1">
					        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
					        	<span aria-hidden="true">&times;</span>
					        </button>
				        </div>
				      <div class="modal-body">
				        <img src="${picPath}/${image.imgFileName}" class="card-img-top">
				      </div>
				      <div class="card-footer bg-transparent">
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
				  </div>
				</div>
			<#else>
				lul
			</#list>
		</div>
	</div>
</div>

</@shell.htmlPage>