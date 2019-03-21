<#import "parts/HTMLshell.ftl" as shell> 
<#import "parts/showLists/showListsOfUsers.ftl" as userLists> 
<#import "parts/showLists/showGroup.ftl" as groupList> 
<@shell.htmlPage> 

<#include "parts/security.ftl">

<div class="col-8">
	<nav>
		<div class="nav nav-pills mr-2" role="tablist">
			<a class="nav-item nav-link mr-1 <#if listType == 'groupSubscriptions'>active</#if>" data-toggle="tab" href="#nav-groupSubs" role="tab">
				${group.groupName}'s subs
			</a>
			<a class="nav-item nav-link mr-1 <#if listType == 'groupAdmins'>active</#if>" data-toggle="tab" href="#nav-groupAdmins" role="tab">
				${group.groupName}'s admins
			</a>
		</div>		
	</nav>
	<div class="tab-content shadow mt-2" id="nav-tabContent" style="background-color: white;">
		<div class="tab-pane fade <#if listType == 'groupSubscriptions'>show active</#if>" id="nav-groupSubs" role="tabpanel" 
			aria-labelledby="nav-subscriptions-tab" style="padding: 15px 5px;">
			<@userLists.showUsers groupSubscrabers "groupSubscriptions" />
		</div>
		<div class="tab-pane fade <#if listType == 'groupAdmins'>show active</#if>" id="nav-groupAdmins" role="tabpanel" 
			aria-labelledby="nav-subscriptions-tab" style="padding: 15px 5px;">
			<@userLists.showUsers groupAdmins "groupAdmins" />
		</div>
	</div>
</div>

</@shell.htmlPage>