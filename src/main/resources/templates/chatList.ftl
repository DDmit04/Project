<#import "parts/HTMLshell.ftl" as shell> 
<#import "/parts/utils/showPics.ftl" as picture>
<@shell.htmlPage> 

<#include "/parts/security.ftl">

<div class="col-9">
	<a href="/createChat" class="btn btn-primary btn-block" role="button">create new chat</a>
	<#list chatSessions as session>
		<#assign chat = session.connectedChat
				 newMessagesCount = DateUtills.calculateNewMessages(chat.chatMessages, session.lastView)>
		<ul class="list-group">
		  <li class="list-group-item d-flex justify-content-between align-items-center mt-2 shadow" style="border-left: none;
																								 		   border-right: none;
																								 		   border-top: none">
			   <div class="border-secondary media">
			  		<@picture.pic chat "mediumPic" "chatList" />
					<div class="media-body">
						<a href="/chats/${chat.id}" class="h4 ml-2">${chat.chatName}</a>
						<div class="ml-2">
							${chat.chatTitle!"no title"}
						</div>
					</div>
					<#if newMessagesCount gt 0>
						<span class="badge badge-primary row ml-2 mt-1">${newMessagesCount}</span>
					</#if>
				</div>
				<div class="text-right">
					<!-- Modal trigger -->
					<button type="button" class="close" data-toggle="modal" data-target="#exampleModal">
						<span aria-hidden="true">&times;</span>
        			</button>
        		</div>
			</li>
		</ul>
		
		<!-- Modal -->
		<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="exampleModalLabel">Confirm action</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body">
		      	<#if chat.chatOwner == currentUser && chat.chatMembers?size gt 2>
		      		<div>You can not delete chat while owning it!</div>
		      	<#else>
			      	<div>Are you sure you want to delete chat: ${chat.chatName}?</div>
			        <div>(you can not restore access to deleted chat)</div>
		        </#if>
		      </div>
		      <div class="modal-footer">
		      	<#if chat.chatOwner != currentUser || chat.chatMembers?size == 1>
			        <button type="button" class="btn btn-secondary" data-dismiss="modal">No</button>
			        <a href="chats/${chat.id}/${currentUser.id}/delete" role="button" class="btn btn-primary">Yes</a>
		        </#if>
		      </div>
		    </div>
		  </div>
		</div>
		
	<#else>
		<h4 class="display-4 ml-2 mt-3 " align="center">No chats :(</h4>
	</#list>
</div>

</@shell.htmlPage>