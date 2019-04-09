<#import "parts/HTMLshell.ftl" as shell>
<#import "parts/showLists/showListsOfUsers.ftl" as userLists> 
<#import "/parts/utils/showPics.ftl" as picture>
<@shell.htmlPage> 

<#include "/parts/security.ftl">

    <noscript>
      <h2>Sorry! Your browser doesn't support Javascript</h2>
    </noscript>
<div class="col-9">
    <div id="chat-page" class="hidden">
        <div class="chat-container" >
            <div class="chat-header">
                <h2>
                	<@picture.pic chat "mediumPic" "chatList" /> 
                	chat room: ${chat.chatName}
                		<!-- Button trigger modal -->
						<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#invates">invate user</button>
					<!-- Button trigger modal -->
					<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#chatMembers">chat information</button>
					<#if chatMembers?seq_contains(currentUser)>
						<a href="/chats/${chat.id}/${currentUser.id}/leave" class="btn btn-primary" role="button">leave</a>
					<#elseif !chatMembers?seq_contains(currentUser) && chatArcive?seq_contains(currentUser)>
						<a href="/chats/${chat.id}/${currentUser.id}/return" class="btn btn-primary" role="button">return</a>
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
						<div class="modal fade" id="chatMembers" tabindex="-1" role="dialog" aria-labelledby="chatMembers" aria-hidden="true">
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
				<#assign isMessageAuthor = (message.messageAuthor.username == currentUsername) >
	    			<li class="list-group-item mt-2">
	    				<div class="mx-2 <#if isMessageAuthor>text-left<#else>text-right</#if>">
	    					<#if isMessageAuthor>
	    						<@picture.pic message.messageAuthor "smallPic" "userPic" /> ${message.messageText}
	    					<#else>
	    						${message.messageText} <@picture.pic message.messageAuthor "smallPic" "userPic" />
	    					</#if>
	    				</div>
	    			</li>
	    		</#list>
    		</ul>
    			<#if chatMembers?seq_contains(currentUser)>
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
<input type="hidden" id="userPicName" value="${user.userPicName!''}"/>

    <script>
    	var currentUsername = ${user.username};
    	var userId = ${user.id};
    	var chatId = ${chat.id};
    </script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.4/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <script src="/js/main.js"></script>
</@shell.htmlPage> 