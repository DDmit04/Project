<#import "parts/HTMLshell.ftl" as shell> 
<#import "parts/showLists/showListsOfUsers.ftl" as userLists> 
<#import "parts/showLists/showGroup.ftl" as groupList> 
<@shell.htmlPage> 

<#include "parts/security.ftl">

<div class="col-8">
	<h5 class="display-5 mt-2 mb-3" align="left">
		${group.groupName}'s connections list
	</h5>
	<nav>
		<div class="nav nav-pills mr-2" role="tablist">
			<a class="nav-item nav-link mr-1 btn-outline-primary <#if listType == 'groupSubscrabers'>active</#if>" data-toggle="tab" 
				href="#nav-groupSubs" role="tab">
				subs (${group.subCount})
			</a>
			<a class="nav-item nav-link mr-1 btn-outline-primary <#if listType == 'groupAdmins'>active</#if>" data-toggle="tab" 
				href="#nav-groupAdmins" role="tab">
				admins (${group.adminCount})
			</a>
			<#if groupAdmins?seq_contains(currentUser)>
				<a class="nav-item nav-link mr-1 btn-outline-primary <#if listType == 'groupBanList'>active</#if>" data-toggle="tab" 
					href="#nav-groupBanList" role="tab">
					ban List (${group.groupBanCount})
				</a>
			</#if>
		</div>		
	</nav>
	<div class="tab-content shadow mt-2" id="nav-tabContent" style="background-color: white;">
		<div class="tab-pane fade <#if listType == 'groupSubscrabers'>show active</#if>" id="nav-groupSubs" role="tabpanel" 
			aria-labelledby="nav-subscriptions-tab" style="padding: 15px 5px;">
			<@userLists.showUsers groupSubscrabers "groupSubscrabers" />
		</div>
		<div class="tab-pane fade <#if listType == 'groupAdmins'>show active</#if>" id="nav-groupAdmins" role="tabpanel" 
			aria-labelledby="nav-subscriptions-tab" style="padding: 15px 5px;">
			<@userLists.showUsers groupAdmins "groupAdmins" />
		</div>
		<#if groupAdmins?seq_contains(currentUser)>
			<div class="tab-pane fade <#if listType == 'groupBanList'>show active</#if>" id="nav-groupBanList" role="tabpanel" 
				aria-labelledby="nav-subscriptions-tab" style="padding: 15px 5px;">
				<@userLists.showUsers groupBanList "groupBanList" />
			</div>
		</#if>
	</div>
</div>

</@shell.htmlPage>