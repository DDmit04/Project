<#import "parts/HTMLshell.ftl" as shell>
<#import "parts/showLists/showFriendRequest.ftl" as friendRequest>

<@shell.htmlPage>

<div class="col-9">
	<h3>Requests sent by you:</h3>
	<@friendRequest.showFriendRequest friendRequestsFrom "from" />
</div>

<hr class="my-5">

<div class="col-9">
	<h3>Requests sent to you:</h3>
	<@friendRequest.showFriendRequest friendRequestsTo "to" />
</div>

</@shell.htmlPage> 