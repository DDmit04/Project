<#import "parts/HTMLshell.ftl" as shell>
<#import "parts/showLists/showFriendRequest.ftl" as friendRequest>

<@shell.htmlPage>

<div class="col-9">
	<h5 class="display-5 mt-2 mb-3" align="left">
		Your friend requests:
	</h5>
	<nav>
		<div class="nav nav-pills mr-2" role="tablist">
			<a class="nav-item nav-link mr-1 btn-outline-primary <#if listType == 'friendRequestsTo'>active</#if>" data-toggle="tab" 
				href="#nav-requestTo" role="tab">
					request from (${friendRequestsToCount})
			</a>
			<a class="nav-item nav-link mr-1 btn-outline-primary <#if listType == 'friendRequestsFrom'>active</#if>" data-toggle="tab" 
				href="#nav-requestFrom" role="tab">
					request to (${friendRequestsFromCount})
			</a> 
		</div>
	</nav>
	<div class="tab-content shadow mt-2" id="nav-tabContent" style="background-color: white;">
		<div class="tab-pane fade <#if listType == 'friendRequestsTo'>show active</#if>" id="nav-requestTo" role="tabpanel" 
			aria-labelledby="nav-subscriptions-tab" style="padding: 15px 5px;">
			<@friendRequest.showFriendRequest friendRequestsTo "to" />
		</div>
		<div class="tab-pane fade <#if listType == 'friendRequestsFrom'>show active</#if>" id="nav-requestFrom" role="tabpanel" 
			aria-labelledby="nav-subscriptions-tab" style="padding: 15px 5px;">
			<@friendRequest.showFriendRequest friendRequestsFrom "from" />
		</div>
	</div>
</div>

</@shell.htmlPage> 