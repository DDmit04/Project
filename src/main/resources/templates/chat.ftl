<#import "parts/HTMLshell.ftl" as shell>
<#import "parts/showLists/showListsOfUsers.ftl" as userLists> 
<#import "/parts/utils/showPics.ftl" as picture>
<@shell.htmlPage> 

<#include "/parts/security.ftl">

<noscript>
	<h2>Sorry! Your browser doesn't support Javascript</h2>
</noscript>
<#if !listType??>
	<#assign listType = "">
</#if>
<#assign currentUserIsChatMember = chatMembers?seq_contains(currentUser)>
<div class="col-9">
    <div id="chat-page" class="hidden">
        <div class="chat-container" >
            <div class="chat-header">
                <h2>
                	<@picture.pic chat "mediumPic" "chatList" /> 
                	chat room: ${chat.chatName}
                	<#if !chatBanList?seq_contains(currentUser)>
						<button class="btn btn-light round" id="dropdownMenuButton" data-toggle="dropdown">
							<i class="fas fa-caret-down"></i>
						</button>
						<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
							<#if currentUserIsChatMember>
								<!-- Button trigger modal -->
								<a class="dropdown-item" data-toggle="modal" data-target="#invates">invate user</a>
								<!-- Button trigger modal -->
								<a class="dropdown-item" data-toggle="modal" data-target="#chatInfo">chat information</a>
								<a href="/chats/${chat.id}/${currentUser.id}/leave" class="dropdown-item">leave</a>
							<#else>
								<a href="/chats/${chat.id}/${currentUser.id}/return" class="dropdown-item">return</a>
							</#if>
								<a href="/chats/${chat.id}/album" class="dropdown-item">chat album</a>
						</div>
					</#if>
					<a href="/${chat.chatOwner.id}/profile" class="btn btn-primary">chat owner</a>
					<#if chat.chatOwner == currentUser || chatAdmins?seq_contains(currentUser)>
						<a href="/chats/${chat.id}/settings" class="btn btn-primary">chat settings</a>
					</#if>
				</h2>
				<!-- Modal -->
				<div class="modal fade" id="invates" tabindex="-1" role="dialog" aria-labelledby="invates" aria-hidden="true">
				  <div class="modal-dialog modal-lg" role="document">
				    <div class="modal-content">
				      <div class="modal-header">
				        <h5 class="modal-title" id="exampleModalLabel">invate user</h5>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
				          <span aria-hidden="true">&times;</span>
				        </button>
				      </div>
				      <div class="modal-body container-fluid">
				      	<#include "/parts/showLists/showUserConnections.ftl">
				      </div>
				    </div>
				  </div>
				</div>
				<!-- Modal -->
				<div class="modal fade" id="chatInfo" tabindex="-1" role="dialog" aria-labelledby="chatInfo" aria-hidden="true">
				  <div class="modal-dialog modal-lg" role="document">
				    <div class="modal-content">
				      <div class="modal-header">
				        <h5 class="modal-title" id="exampleModalLabel">chat members</h5>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
				          <span aria-hidden="true">&times;</span>
				        </button>
				      </div>
				      <div class="modal-body">
				      	<#include "/chatConnections.ftl">
				      </div>
				    </div>
				  </div>
				</div>
                <span>${chat.chatTitle!"no title"}</span>
            </div>
            <ul class="list-group " id="messageArea" style="height: 500px;
															overflow: auto;
															top:0;">
				<#list chatMessages as message>
					<#assign currentUserIsMessageAuthor = (message.messageAuthor.username == currentUsername)>
	    			<li class="list-group-item mt-2">
	    				<div class="<#if currentUserIsMessageAuthor>text-left<#else>text-right</#if>">
	    					<#if currentUserIsMessageAuthor>
	    						<@picture.pic message.messageAuthor "smallPic" "userPic" />${message.messageText}
	    					<#else>
	    						${message.messageText} <@picture.pic message.messageAuthor "smallPic" "userPic" />
	    					</#if>
	    				</div>
	    			</li>
		    	</#list>
    		</ul>
    			<#if currentUserIsChatMember>
		            <form id="messageForm" name="messageForm" nameForm="messageForm">
		                <div class="form-group mt-2">
		                    <div class="input-group clearfix">
		                        <input type="text" id="message" placeholder="Type a message..." class="form-control"/>
		                        <button type="submit" class="btn btn-primary ml-2">Send</button>
		                    </div>
		                </div>
		            </form>
	            </#if>
        </div>
    </div>
</div>
<#if user.userImage??>
	<#assign userPicValue = user.userImage.imgFileName>
<#else>
	<#assign userPicValue = ''>
</#if>
<input type="hidden" id="isChatActive" value="<#if currentUserIsChatMember>1<#else>0</#if>"/>
<input type="hidden" id="userPicName" value="${userPicValue}"/>

    <script>
    	var currentUsername = ${user.username};
    	var userId = ${user.id};
    	var chatId = ${chat.id};
    </script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.4/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <script src="/js/main.js"></script>
</@shell.htmlPage> 