<#import "parts/HTMLshell.ftl" as shell>
<#import "/parts/utils/showPics.ftl" as picture>
<@shell.htmlPage> 

<#include "/parts/security.ftl">

    <noscript>
      <h2>Sorry! Your browser doesn't support Javascript</h2>
    </noscript>
<div class="col-8">
    <div id="chat-page" class="hidden">
        <div class="chat-container">
            <div class="chat-header">
                <h2>
                	<@picture.pic chat "mediumPic" "chatList" /> 
                	chat room: ${chat.chatName}
                	<a href="" class="btn btn-primary" role="button">add users</a>
                	<a href="" class="btn btn-primary" role="button">leave</a>
                </h2>
            </div>
            <ul class="list-group" id="messageArea">
				<#list chatMessages as message>
	    			<li class="list-group-item mt-2">
	    				<div class="<#if message.messageAuthor.username == currentUsername>text-left<#else>text-right</#if>">
			    			<@picture.pic message.messageAuthor "smallPic" "userPic" /> ${message.messageText}
	    				</div>
	    			</li>
	    		</#list>
    		</ul>
            <form id="messageForm" name="messageForm" nameForm="messageForm">
                <div class="form-group mt-2">
                    <div class="input-group clearfix">
                        <input type="text" id="message" placeholder="Type a message..." class="form-control"/>
                        <button type="submit" class="btn btn-primary ml-2">Send</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
    <script>
    	var username = ${currentUser.username};
    	var chatId = ${chat.id};
    	alert(chatId);
    	var currentUserPic = ${currentUserPicName};
    </script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.4/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <script src="/js/main.js"></script>
</@shell.htmlPage> 