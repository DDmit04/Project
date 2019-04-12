<#import "parts/showLists/showListsOfUsers.ftl" as userLists> 

<#include "parts/security.ftl">

	<h5 class="display-5 mt-2 mb-3" align="left">
		${chat.chatName}'s connections list
	</h5>
	<nav>
		<div class="nav nav-pills mr-2" role="tablist">
			<a class="nav-item nav-link mr-1 btn-outline-primary" data-toggle="tab" 
				href="#nav-chatAdmins" role="tab">
				admins (${chat.adminCount})
			</a>
			<a class="nav-item nav-link mr-1 btn-outline-primary" data-toggle="tab" 
				href="#nav-chatMembers" role="tab">
				chat members (${chat.membersCount})
			</a>
			<#if chatAdmins?seq_contains(currentUser)>
				<a class="nav-item nav-link mr-1 btn-outline-primary" data-toggle="tab" 
					href="#nav-chatBans" role="tab">
					ban list (${chat.chatBanCount})
				</a>
			</#if>
		</div>		
	</nav>
	<div class="tab-content shadow mt-2" id="nav-tabContent" style="background-color: white;">
		<div class="tab-pane fade" id="nav-chatAdmins" role="tabpanel" 
			aria-labelledby="nav-subscriptions-tab" style="padding: 15px 5px;">
			<@userLists.showUsers chatAdmins "groupAdmins" />
		</div>
		<div class="tab-pane fade" id="nav-chatMembers" role="tabpanel" 
			aria-labelledby="nav-subscriptions-tab" style="padding: 15px 5px;">
			<@userLists.showUsers chatMembers "chatMembers" />
		</div>
		<#if chatAdmins?seq_contains(currentUser)>
			<div class="tab-pane fade" id="nav-chatBans" role="tabpanel" 
				aria-labelledby="nav-subscriptions-tab" style="padding: 15px 5px;">
				<@userLists.showUsers chatBanList "chatBans" />
			</div>
		</#if>
	</div>
